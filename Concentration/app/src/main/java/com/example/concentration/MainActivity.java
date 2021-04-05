package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final String[] words = {"PANDA", "TIGER","FISH", "BIRD", "BUTTERFLY", "DOG", "CAT", "COW", "HORSE", "MONKEY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay = (Button)findViewById(R.id.btnEndGame);
        Button btnTryAgain = (Button)findViewById(R.id.btnTryAgain);
        Button btnNewGame = (Button)findViewById(R.id.btnNewGame);
        Button btnEndGame = (Button)findViewById(R.id.btnEndGame);

        // Listener
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the screen of concentration game



            }
        });

    }
}