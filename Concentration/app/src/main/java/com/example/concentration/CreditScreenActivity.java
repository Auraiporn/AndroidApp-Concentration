package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CreditScreenActivity extends AppCompatActivity {

    //Variables
    Animation rightAnim;
    Button btnback;
    TextView Title, Team, Haowen, Beize, Amir, Auraiporn;
    pl.droidsonroids.gif.GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_screen);

        //Animations
        rightAnim = AnimationUtils.loadAnimation(this,R.anim.right_animation);


        //Hooks
        Button btnback = (Button)findViewById(R.id.btnback);
        TextView credits = (TextView)findViewById(R.id.Title);
        TextView title = (TextView)findViewById(R.id.Team);
        TextView haowen = (TextView)findViewById(R.id.Haowen);
        TextView beize = (TextView)findViewById(R.id.Beize);
        TextView amir = (TextView)findViewById(R.id.Amir);
        TextView auraiporn = (TextView)findViewById(R.id.Auraiporn);

        btnback.setAnimation(rightAnim);
        credits.setAnimation(rightAnim);
        title.setAnimation(rightAnim);
        haowen.setAnimation(rightAnim);
        beize.setAnimation(rightAnim);
        amir.setAnimation(rightAnim);
        auraiporn.setAnimation(rightAnim);

    //listener for back button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the screen of concentration game
                openMainActivity();
            }
        });
    }
    // open main screen when play button is pressed.
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}