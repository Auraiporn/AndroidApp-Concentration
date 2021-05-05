package com.example.concentration;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class Card extends androidx.appcompat.widget.AppCompatButton {

    private Context context;
    private String assignedWord;
    private int background;

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public String flip(){
        if(background==R.drawable.blue){
            this.background = R.drawable.white;
            this.setBackgroundResource(background);
            this.setText(assignedWord);
        }
        else{
            this.background = R.drawable.blue;
            this.setBackgroundResource(background);
            this.setText("");
        }
        return this.assignedWord;
    }
    public void setWord(String word){
        this.assignedWord = word;
    }

    public Card(Context context) {
        super(context);
        //quick settings
        String defaultText = "";
        int background = R.drawable.blue;
        int textColor = R.color.black;
        int cardHeight = 80;
        int cardWidth = 70;

        //Initialization
        this.context = context;
        this.assignedWord = "";
        this.setText(defaultText);
        this.setBackgroundResource(background);
        this.background = background;
        this.setTextColor(getResources().getColor(textColor));

        //Modify card size
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0,0,0,0);
        params.height = dpToPx(cardHeight,context);
        params.width = dpToPx(cardWidth,context);
        this.setLayoutParams(params);
    }

}
