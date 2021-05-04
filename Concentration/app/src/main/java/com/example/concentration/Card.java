package com.example.concentration;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class Card extends androidx.appcompat.widget.AppCompatButton {

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public String test(){
        return "hello";
    }

    public Card(Context context) {
        super(context);
        this.setText("");
        this.setBackgroundResource(R.drawable.blue);
        this.setTextColor(getResources().getColor(R.color.black));

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0,0,0,0);
        params.height = dpToPx(80,context);
        params.width = dpToPx(70,context);
        this.setLayoutParams(params);
    }

}

//<Button
//        android:id="@+id/BtnTestCard"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:background="@drawable/blue"
//                android:text=""
//                android:textColor="@color/black"
//                app:backgroundTint="@null"
//                android:layout_centerInParent="true"
//                android:scaleX="0.7"
//                android:scaleY="0.7"/>
