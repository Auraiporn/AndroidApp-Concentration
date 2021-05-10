package com.example.concentration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    //Variables
    private Animation topAnim, bottomAnim;
    private Button btnStartGame, btnHighScore, btnCredit;
    private pl.droidsonroids.gif.GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        //Hooks
        Button btnPlay = (Button)findViewById(R.id.btnStartGame);
        Button btnScore = (Button)findViewById(R.id.btnHighScore);
        Button btnCredit = (Button)findViewById(R.id.btnCredit);
        pl.droidsonroids.gif.GifImageView Giphy = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.gif);

        btnPlay.setAnimation(bottomAnim);
        btnScore.setAnimation(bottomAnim);
        btnCredit.setAnimation(bottomAnim);
        Giphy.setAnimation(topAnim);

        // Listener for Play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               spawnDialog("Choose the amount of cards","Play");
                //Open game screen
           //    openGameScreenActivity();
            }
        });
        //Listener for Credit Button
        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open credit screen
                openCreditScreenActivity();
            }
        });
        // Listener for High Score button
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spawnDialog("Choose the game type","HighScore");
                //openScoreScreenActivity();
            }
        });
    }

    //Input: prompt you want to display; option is to distinguish Play button and HighScore button
    //Output: None
    //Note: Create a dialog to select number of cards.
    private void spawnDialog(String prompt, String option){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_spinner_playbutton,null);
        builder.setTitle(prompt);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_playButton);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.card_options));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(option=="Play") {
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!spinner.getSelectedItem().toString().equalsIgnoreCase(getResources().getStringArray(R.array.card_options)[0])){
                        openGameScreenActivity(spinner.getSelectedItem().toString());
                    }
                }
            });
        }
        else if(option == "HighScore") {
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!spinner.getSelectedItem().toString().equalsIgnoreCase(getResources().getStringArray(R.array.card_options)[0])){
                        openScoreScreenActivity(spinner.getSelectedItem().toString());
                    }
                }
            });
        }
        else{
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
    //Note: open game screen when play button is pressed.
    public void openGameScreenActivity(String cards){
       Intent intent = new Intent(this, GameScreenActivity.class);
       intent.putExtra("extra_cardNumber", cards);
       startActivity(intent);
    }
    //Input: None
    //Output: None
    //Note: open high score screen when high score button is pressed.
    public void openScoreScreenActivity(String gameType){
        Intent intent = new Intent(this, ScoreScreenActivity.class);
        intent.putExtra("extra_gameType",gameType);
        startActivity(intent);
    }
    //Input: None
    //Output: None
    //Note: open credit screen when credits button is pressed.
    public void openCreditScreenActivity(){
        Intent intent = new Intent(this, CreditScreenActivity.class);
        startActivity(intent);
    }
}