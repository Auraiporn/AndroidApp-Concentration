package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   //Variables
    Animation topAnim, bottomAnim;
    Button btnStartGame, btnHighScore, btnCredit;
    pl.droidsonroids.gif.GifImageView gif;

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
               //Open the screen of concentration game
               openGameScreenActivity();
            }
        });
        //Listener for Credit Button
        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreditScreenActivity();
            }
        });
        // Listener for High Score button
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the screen of concentration game
                openScoreScreenActivity();
            }
        });
    }
    // open game screen when play button is pressed.
    public void openGameScreenActivity(){
       Intent intent = new Intent(this, GameScreenActivity.class);
       startActivity(intent);
    }
    // open high score screen when high score button is pressed.
    public void openScoreScreenActivity(){
        Intent intent = new Intent(this, ScoreScreenActivity.class);
        startActivity(intent);
    }
    // open credit screen when credits button is pressed.
    public void openCreditScreenActivity(){
        Intent intent = new Intent(this, CreditScreenActivity.class);
        startActivity(intent);
    }
}