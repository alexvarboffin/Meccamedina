package com.walhalla.ytlib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.walhalla.ui.DLog;

public class ServiceUtils {

    private boolean isServiceRunning(Activity activity, String serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                String xxx = service.service.getClassName();
                DLog.d("@@@@@" + xxx);
                if (serviceClass.equals(xxx)) {
                    return true;
                }
            }
        }
        return false;
    }
}
