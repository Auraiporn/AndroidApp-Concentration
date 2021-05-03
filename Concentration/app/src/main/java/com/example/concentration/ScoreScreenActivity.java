package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreenActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    TextView textview1;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        Button btnback = (Button) findViewById(R.id.btnback);
        TextView HighScore = (TextView) findViewById(R.id.textview1);
        HighScore.setAnimation(topAnim);
        btnback.setAnimation(bottomAnim);

        //Listener for back button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the main screen
                openMainActivity();
            }
        });
    }
    // open main screen when back is pressed.
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}