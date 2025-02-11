package com.demo.scrapper;


import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

public class SdkUtils {

    private static final String TAG = "@@@";

    // check the Accessibility
    public static boolean isAccessibilityOn(Context context, Class<? extends AccessibilityService> clazz) {
        Log.d(TAG, "check the Accessibility");
        int accessibilityEnabled = 0;
        final String service = context.getPackageName() + "/" + clazz.getCanonicalName();
        Log.d(TAG, "String service: " + service);
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 0);
            Log.d(TAG, "AccessibilityEnabled:" + accessibilityEnabled);
        } catch (Exception e) {
            Log.e(TAG, "get accessibility enable failed, the err:" + e.getMessage());
        }
        Log.d(TAG, "debug for accessibility.");

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue == null)
                Log.d(TAG, "settingValue == null");
            if (settingValue != null) {

                colonSplitter.setString(settingValue);

                Log.d(TAG, "colonSplitter:" + colonSplitter);
                Log.d(TAG, "@settingValue@" + settingValue);

                while (colonSplitter.hasNext()) {
                    String accessibilityService = colonSplitter.next();

                    Log.d(TAG, "###0" + accessibilityService);
                    Log.d(TAG, "###1" + service);

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.d(TAG, "Accessibility service disable");
        }
        return false;
    }
}