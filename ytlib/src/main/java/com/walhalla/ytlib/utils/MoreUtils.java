package com.walhalla.ytlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

public class MoreUtils {

    public void hideKeyboard(Activity activity, View view) {
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static List<ResolveInfo> getCameraAppsResolveInfo(Context context) {
        List<ResolveInfo> resolveInfo = new ArrayList<>();
        if (null == (context)) {
            return resolveInfo;
        }
        final Intent capturePhoto = new Intent("android.intent.action.MAIN");
        PackageManager pm = context.getPackageManager();
        resolveInfo = pm.queryIntentActivities(capturePhoto, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 we need to add specific camera apps
            // due them are not added during ACTION_IMAGE_CAPTURE scanning...
            resolveInfo.addAll(getCameraSpecificAppsInfo(context));
        }
        return resolveInfo;
    }

    /**
     * For Android 11
     * Return camera possible apps
     */
    static final String[] CAMERA_SPECIFIC_APPS = new String[]{
            "com.google.android.youtube"
    };

    private static List<ResolveInfo> getCameraSpecificAppsInfo(Context context) {
        List<ResolveInfo> resolveInfo = new ArrayList<>();
        if (null == (context)) {
            return resolveInfo;
        }
        PackageManager pm = context.getPackageManager();
        for (String packageName : CAMERA_SPECIFIC_APPS) {
            resolveInfo.addAll(getCameraSpecificAppInfo(packageName, pm));
        }
        return resolveInfo;
    }

    private static List<ResolveInfo> getCameraSpecificAppInfo(String packageName, PackageManager pm) {
        Intent specificCameraApp = new Intent("android.intent.action.MAIN");
        specificCameraApp.setPackage(packageName);
        return pm.queryIntentActivities(specificCameraApp, 0);
    }

}
