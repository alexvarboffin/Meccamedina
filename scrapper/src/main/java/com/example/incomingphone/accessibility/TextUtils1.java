package com.example.incomingphone.accessibility;

import android.content.Context;

import com.google.gson.Gson;
import com.pinterest.PinterestUsers;

import java.io.IOException;
import java.io.InputStream;

public class TextUtils1 {


        public static PinterestUsers readConfig(Context context){
            Gson gson = new Gson();
            return gson.fromJson(loadJSONFromAsset(context), PinterestUsers.class);
        }

        public static String loadJSONFromAsset(Context context) {
            String json = null;
            try {
                InputStream is = context.getAssets().open("data.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }
