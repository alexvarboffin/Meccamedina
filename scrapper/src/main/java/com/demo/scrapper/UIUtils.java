package com.demo.scrapper;

import android.content.Context;
import android.widget.Toast;

public class UIUtils {
    public static void showToast0(Context context, String s) {
        Toast.makeText(context, String.valueOf(s), Toast.LENGTH_SHORT).show();
    }
}
