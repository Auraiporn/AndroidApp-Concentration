package com.example.concentration;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import java.io.Serializable;

public class Card extends androidx.appcompat.widget.AppCompatButton {

    private Context context;
    private String assignedWord;
    private int background;
    private boolean freeze;

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public void freeze(){
        this.setAlpha((float) 0.5);
        this.setEnabled(false);
        this.freeze = true;
    }
    public void reset(){
        this.setAlpha(1);
        this.background = R.drawable.blue;
        this.setBackgroundResource(background);
        this.setText("");
        this.setEnabled(true);
        this.freeze = false;
    }
    public void reveal(){
        this.background = R.drawable.white;
        this.setBackgroundResource(background);
        this.setText(assignedWord);
        this.freeze();
    }
    public String limitFlip(){
        this.background = R.drawable.white;
        this.setBackgroundResource(background);
        this.setText(assignedWord);
        this.setEnabled(false);
        return assignedWord;
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
    public String getWord(){return this.assignedWord;}
    public String getCardBackground(){return Integer.toString(this.background);}
    public String getFreeze(){
        if(freeze){
            return "true";
        }
        else{
            return "false";
        }
    }
    public void loadInfo(String info){
        String [] ary_info = info.split(",");
        this.setWord(ary_info[0]);
        this.background = Integer.parseInt(ary_info[1]);
        if(ary_info[2].equals("true")){
            this.freeze = true;
        }
        else{
            this.freeze = false;
        }
    }
    public boolean equalTo(Card card){
        if(this.assignedWord.equals(card.getWord())){
            return true;
        }
        return false;
    }
    public void setCurrent(){
        this.setBackgroundResource(background);
        if(background == R.drawable.white){
            this.setText(assignedWord);
        }
        if(freeze){
            this.freeze();
        }
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
        this.freeze = false;

        //Modify card size
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0,0,0,0);
        params.height = dpToPx(cardHeight,context);
        params.width = dpToPx(cardWidth,context);
        this.setLayoutParams(params);
    }
}
