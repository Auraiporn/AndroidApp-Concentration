package com.example.concentration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ScoreScreenActivity extends AppCompatActivity {

    //Variables
    private Animation rightAnim;
    private Button btnBack;
    private TextView HighScore;
    private String gameType;
    private ArrayList<TextView> txtList;
    private ArrayList<String> allPlayerInfo;
    private PriorityQueue<Player> pq;
    private static final String FILE_NAME = "score.txt";

    //Input: None
    //Output: None
    //Note: read local file for records
    public void read() throws IOException {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String temp;
            while((temp = br.readLine()) != null){
                String [] info = temp.split(",");
                if(info[2].equals(gameType)){
                    allPlayerInfo.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis!=null){
                fis.close();
            }
        }
    }
    //Input: None
    //Output: None
    //Note: Loop through all information read
    // and pickup corresponding game type and put the data into Priority Queue.
    public void load(){
        for(String s : allPlayerInfo){
            String [] info = s.split(",");
            Player player = new Player(info[0],Integer.parseInt(info[1]));
            pq.add(player);
        }
        if(!pq.isEmpty()){
            for(int i = 0; i < txtList.size(); i++){
                txtList.get(i).setText(pq.peek().getName()+" : " + Integer.toString(pq.peek().getScore()));
                pq.poll();
                if(pq.isEmpty()){
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        //Initialization
        gameType = getIntent().getStringExtra("extra_gameType");
        txtList = new ArrayList<TextView>();
        allPlayerInfo = new ArrayList<String>();
        pq = new PriorityQueue<Player>(5,new PlayerComparator());

        txtList.add(findViewById(R.id.txtTop1));
        txtList.add(findViewById(R.id.txtTop2));
        txtList.add(findViewById(R.id.txtTop3));
        txtList.add(findViewById(R.id.txtTop4));
        txtList.add(findViewById(R.id.txtTop5));

        for(TextView tv: txtList){
            tv.setText("ABC : ...");
        }

        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();

        //Animations
        rightAnim = AnimationUtils.loadAnimation(this,R.anim.right_animation);

        //Hooks
        btnBack = (Button) findViewById(R.id.btnBack);
        HighScore = (TextView) findViewById(R.id.txtScoreScreenTitle);
        HighScore.setAnimation(rightAnim);
        btnBack.setAnimation(rightAnim);

        //Listener for back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the main screen
                openMainActivity();
                finish();
            }
        });
    }
    //Input: None
    //Output: None
    //Note: open main screen when back is pressed.
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}