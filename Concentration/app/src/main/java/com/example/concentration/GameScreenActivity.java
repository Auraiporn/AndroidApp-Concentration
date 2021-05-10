package com.example.concentration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameScreenActivity extends AppCompatActivity implements View.OnClickListener, Dialog_Score.DialogListener {

    //Variables
    String[] words;
    private ArrayList<Card> card_list;
    private Button btnEnd, btnSoundOn, btnSoundOff;
    private MediaPlayer player;
    private GridLayout field;
    private TextView txtScore;
    private int playerScore;
    private Stack<Integer> cardStack;
    private Button btnTryAgain, btnNewGame;
    private boolean isFreeze;
    private String userName;
    private static final String FILE_NAME = "score.txt";

    //Show message for testing purpose
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    //Input: array that is used as a map
    //Output: random index of a empty slot
    //Note: returns -1 if array is full
    private int getRandomAvailableIndex(int[] ary) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(ary.length);
        //Check if array is full
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == 0) {
                break;
            }
            if (i == ary.length - 1 && ary[i] != 0) {
                return -1;
            }
        }
        while (ary[randomIndex] > 0) {
            randomIndex = rand.nextInt(ary.length);
        }
        return randomIndex;
    }

    //Input: None
    //Output: None
    //Note: Update the score with corresponding TextView
    private void updateScore() {
        if (playerScore < 0) {
            playerScore = 0;
        }
        txtScore.setText("Score: " + playerScore);
    }

    //Input: None
    //Output: None
    //Note: Populate the Card with words
    private void loadWords() {
        int[] word_flag = new int[words.length];
        int[] card_flag = new int[card_list.size()];

        while (getRandomAvailableIndex(card_flag) != -1) {
            int word_index = getRandomAvailableIndex(word_flag);
            word_flag[word_index] += 1;
            //Twice for one word
            int card_index = getRandomAvailableIndex(card_flag);
            card_flag[card_index] += 1;
            card_list.get(card_index).setWord(words[word_index]);
            card_index = getRandomAvailableIndex(card_flag);
            card_flag[card_index] += 1;
            card_list.get(card_index).setWord(words[word_index]);
        }
    }

    //Input: ArrayList<Card>
    //Output: ArrayList<String>
    //Note: Translate the ArrayList<Card> to ArrayList<String> to be save into Bundle
    private ArrayList<String> transferCards(ArrayList<Card> cardList) {
        ArrayList<String> string_card = new ArrayList<String>();
        for (Card c : cardList) {
            String save = "";
            save += c.getWord() + "," + c.getCardBackground() + "," + c.getFreeze();
            string_card.add(save);
        }
        return string_card;
    }

    //Input: Stack<Integer>
    //Output: ArrayList<Integer>
    //Note: Translate stack of card index to ArrayList to be save into Bundle
    private ArrayList<Integer> translateStackToArray(Stack<Integer> stack) {
        ArrayList<Integer> ary = new ArrayList<Integer>();
        if (stack.size() > 0) {
            while (!stack.empty()) {
                ary.add(stack.peek());
                stack.pop();
            }
        }
        return ary;
    }

    //Input: None
    //Output: None
    //Note: Spawn dialogue for score and exit the Activity
    private void openDialog() {
        Dialog_Score dialog = new Dialog_Score();
        dialog.show(getSupportFragmentManager(), "dialog_score");
    }

    //Input: None
    //Output: None
    //Note: Check if the game is over
    private void checkEndGame() {
        float terminal = (float) 0.5;
        for (Card c : card_list) {
            if (Float.compare(c.getAlpha(), terminal) != 0) {
                return;
            }
        }
        Thread wait = new Thread() {
            public void run() {
                try {
                    sleep(1 * 1000);
                    openDialog();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        wait.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Initializations
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnSoundOn = (Button) findViewById(R.id.btnSoundOn);
        btnSoundOff = (Button) findViewById(R.id.btnSoundOff);
        words = getResources().getStringArray(R.array.game_words);
        card_list = new ArrayList<Card>();
        txtScore = findViewById(R.id.txtScore);
        playerScore = 0;
        cardStack = new Stack<Integer>();
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        isFreeze = false;
        userName = "";
        // Get Stack and score saves
        if (savedInstanceState != null) {
            this.playerScore = savedInstanceState.getInt("score");
            for (Integer x : savedInstanceState.getIntegerArrayList("card_stack")) {
                cardStack.push(x);
            }
            this.isFreeze = savedInstanceState.getBoolean("freeze");
        }
        updateScore();

        //Spawn Card field
        field = findViewById(R.id.cardField);
        for (int i = 0; i < Integer.parseInt(getIntent().getStringExtra("extra_cardNumber")); i++) {
            Card temp = new Card(this);
            temp.setOnClickListener(this);
            card_list.add(temp);
            field.addView(temp);
        }
        // Input words into the cards
        if (savedInstanceState != null) {
            ArrayList<String> card_string_list = savedInstanceState.getStringArrayList("card_list");
            for (int i = 0; i < card_list.size(); i++) {
                card_list.get(i).loadInfo(card_string_list.get(i));
                card_list.get(i).setCurrent();
            }
        } else {
            loadWords();
        }

        //Try Again Button add onCLick()
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardStack.size() > 1) {
                    while (!cardStack.empty()) {
                        card_list.get(cardStack.peek()).reset();
                        cardStack.pop();
                    }
                }
                isFreeze = false;
            }
        });
        // New game Button add OnClick()
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spawnDialog("Choose the amount of cards", "Play");
            }
        });

        // Action listener to start music
        player = MediaPlayer.create(this, R.raw.sb_indreams);
        btnSoundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                player.setLooping(true);

            }
        });
        // Action listener to stop music
        player = MediaPlayer.create(this, R.raw.sb_indreams);
        btnSoundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();

            }
        });

        //listener for End button
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Card c : card_list) {
                    c.reveal();
                }
                Thread wait = new Thread() {
                    public void run() {
                        try {
                            sleep(1 * 1000);
                            checkEndGame();
                        } catch (Exception e) {
                        }
                    }
                };
                // start thread
                wait.start();
            }
        });
    }

    //Handle rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("card_list", transferCards(this.card_list));
        outState.putIntegerArrayList("card_stack", translateStackToArray(this.cardStack));
        outState.putInt("score", playerScore);
        outState.putBoolean("freeze", this.isFreeze);
    }

    //Input: None
    //Output: None
    //Note: Method to release media player
    private void releaseMediaPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    //Input: None
    //Output: None
    //Note: Listener for media player
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    //Input: None
    //Output: None
    //Note: Listener for each card pressed
    @Override
    public void onClick(View v) {
        Card c = (Card) v;
        if (!isFreeze) {
            c.limitFlip();
            cardStack.push(card_list.indexOf(c));
        }
        if (!isFreeze && cardStack.size() > 1) {
            if (card_list.get(cardStack.peek()).equalTo((card_list.get(cardStack.firstElement())))) {
                playerScore += 2;
                card_list.get(cardStack.peek()).freeze();
                card_list.get(cardStack.firstElement()).freeze();
                cardStack.clear();
            } else {
                cardStack.push(card_list.indexOf(c));
                playerScore -= 1;
                isFreeze = true;
            }
        }
        updateScore();
        checkEndGame();
    }

    //Input: None
    //Output: None
    //Note: Triggers when click confirm
    @Override
    public void getUserName(String name, Boolean skip) {
        this.userName = name;
        if (!skip) {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Input: None
    //Output: None
    //Note: Save user name and score to local file.
    public void save() throws IOException {
        String info = this.userName + "," + Integer.toString(this.playerScore) + "," + getIntent().getStringExtra("extra_cardNumber") + "\n";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(info.getBytes());
            showToast("Score Saved!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    //Input: prompt you want to display; option is to distinguish Play button and HighScore button
    //Output: None
    //Note: Restart game package from MainActivity
    private void spawnDialog(String prompt, String option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameScreenActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_spinner_playbutton, null);
        builder.setTitle(prompt);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_playButton);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GameScreenActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.card_options));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (option == "Play") {
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!spinner.getSelectedItem().toString().equalsIgnoreCase(getResources().getStringArray(R.array.card_options)[0])) {
                        openGameScreenActivity(spinner.getSelectedItem().toString());
                    }
                }
            });
        } else {
            return;
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Open the score screen
                // openScoreScreenActivity();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Input: None
    //Output: None
    //Note: Open the GameActivity again for New Game Button
    public void openGameScreenActivity(String cards) {
        Intent intent = new Intent(this, GameScreenActivity.class);
        intent.putExtra("extra_cardNumber", cards);
        startActivity(intent);
        finish();
    }
}