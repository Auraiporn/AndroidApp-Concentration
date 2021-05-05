package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameScreenActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables
    String[] words = {"PANDA", "TIGER","FISH", "BIRD", "Owl", "DOG", "CAT", "COW", "HORSE", "MONKEY"};
    private ArrayList<Card> card_list;

    private Button btnEnd, btnSoundOn, btnSoundOff;
    private MediaPlayer player;
    private GridLayout field;
    private TextView txtScore;
    private int playerScore;
    private Stack<Card> cardStack;

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    //Input: array that is used as a map
    //Output: random index of a empty slot
    //Note: returns -1 if array is full
    private int getRandomAvailableIndex(int ary[]){
        Random rand = new Random();
        int randomIndex = rand.nextInt(ary.length);
        //Check if array is full
        for(int i = 0; i < ary.length; i++){
            if(ary[i]==0){
                break;
            }
            if(i == ary.length-1 && ary[i]!=0) {
                return -1;
            }
        }
        while(ary[randomIndex]>0){
            randomIndex = rand.nextInt(ary.length);
        }
        return randomIndex;
    }
    private void updateScore(){
        if(playerScore < 0){
            playerScore = 0;
        }
        txtScore.setText("Score: "+playerScore);
    }
    private void loadWords(){
        Random rand = new Random();
        int word_flag[] = new int[words.length];
        int card_flag[] = new int[card_list.size()];

        while(getRandomAvailableIndex(card_flag)!=-1){
            int word_index = getRandomAvailableIndex(word_flag);
            word_flag[word_index]+=1;
            //Twice for one word
            int card_index = getRandomAvailableIndex(card_flag);
            card_flag[card_index]+=1;
            card_list.get(card_index).setWord(words[word_index]);
            card_index = getRandomAvailableIndex(card_flag);
            card_flag[card_index]+=1;
            card_list.get(card_index).setWord(words[word_index]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Initialized buttons
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnSoundOn = (Button) findViewById(R.id.btnSoundOn);
        btnSoundOff = (Button)findViewById(R.id.btnSoundOff);
        card_list = new ArrayList<Card>();
        txtScore = findViewById(R.id.txtScore);
        playerScore = 0;
        cardStack = new Stack<Card>();

        //Test Ground for card field
        field = findViewById(R.id.cardField);
        field.setColumnCount(4);
        //field.setRowCount(5);
        for(int i = 0; i < 8; i++){
            Card temp  = new Card(this);
            temp.setOnClickListener(this);
            card_list.add(temp);
            field.addView(temp);
        }
        loadWords();

        // Action listener to start music
        player = MediaPlayer.create(this,R.raw.sb_indreams);
        btnSoundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();

            }
        });
        // Action listener to stop music
        player = MediaPlayer.create(this,R.raw.sb_indreams);
        btnSoundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();

            }
        });
        // Release the music player when music is played completely.
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        });

        //listener for End button
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
    // Method to release media player
    private void releaseMediaPlayer(){
        if(player != null)
        {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public void onClick(View v) {
        Card c = (Card) v;
        c.limitFlip();
        if(cardStack.empty()){
            cardStack.push(c);
        }
        else if(cardStack.size()>1){
            while(!cardStack.empty()){
                cardStack.peek().reset();
                cardStack.pop();
            }
            cardStack.push(c);
        }
        else if(cardStack.peek().equalTo(c)){
            playerScore+=2;
            cardStack.peek().freeze();
            c.freeze();
            cardStack.clear();
        }
        else{
            cardStack.push(c);
            playerScore-=1;
        }
        updateScore();
    }
}