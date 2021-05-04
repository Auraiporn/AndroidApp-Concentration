package com.example.concentration;

import android.content.Context;
import android.view.Gravity;

public class Card extends androidx.appcompat.widget.AppCompatButton {

    public Card(Context context) {
        super(context);
        this.setText("");
        this.setBackgroundResource(R.drawable.blue);
        this.setTextColor(getResources().getColor(R.color.black));
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
