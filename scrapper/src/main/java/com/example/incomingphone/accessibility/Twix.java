package com.example.incomingphone.accessibility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


/*
 *
 * //Smali
 *
 *
 * */

public class Twix extends View {


    public Twix(Context context) {
        super(context);

        setImportantForAccessibility(382);//Disable
        setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);//Disable
        setFocusable(false);//Disable


        setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
        setFocusable(true);
    }

    public Twix(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Twix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public Twix(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
