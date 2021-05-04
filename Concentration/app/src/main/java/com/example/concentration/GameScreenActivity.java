package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

public class GameScreenActivity extends AppCompatActivity {

    //Variables
    String[] words = {"PANDA", "TIGER","FISH", "BIRD", "BUTTERFLY", "DOG", "CAT", "COW", "HORSE", "MONKEY"};

    private Button btnEnd, btnSoundOn, btnSoundOff;
    private MediaPlayer player;

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Initialized buttons
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnSoundOn = (Button) findViewById(R.id.btnSoundOn);
        btnSoundOff = (Button)findViewById(R.id.btnSoundOff);

        //Test Ground for card field


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

//    @Override
//    public void onClick(View v) {
//        if(btnTestCard.getText().toString().equals("")){
//            btnTestCard.setBackgroundResource(R.drawable.white);
//            btnTestCard.setText("Hello");
//        }
//        else{
//            btnTestCard.setBackgroundResource(R.drawable.blue);
//            btnTestCard.setText("");
//        }
//    }
}