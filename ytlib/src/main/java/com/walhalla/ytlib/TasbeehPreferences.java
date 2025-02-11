package com.walhalla.ytlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;

import androidx.preference.PreferenceManager;

import com.walhalla.ui.DLog;

public class TasbeehPreferences {

    public static int minVibrationLevel = 50;
    public static int maxVibrationLevel = 150;

    private static final String KEY_TASBEEH = "Tasbeeh";
    private static final String KEY_CURRENT_RAW = "key_sound";
    private final SharedPreferences preferences;


    public TasbeehPreferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int preferencesTasbeeh() {
        return getPreferences().getInt(KEY_TASBEEH, 0);
    }

    private SharedPreferences getPreferences() {
        return preferences;
    }

    public boolean preferencesTasbeeh(int counter) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(KEY_TASBEEH, counter);
        return editor.commit();
    }

    public int loadCurrentRawSound() {
        return getPreferences().getInt(KEY_CURRENT_RAW, 0);
    }

//    public boolean setCurrentRawSound(int i) {
//        SharedPreferences.Editor editor = getPreferences().edit();
//        editor.putInt(KEY_CURRENT_RAW, i);
//        return editor.commit();
//    }


//    public void setSound_enabled(boolean sound_enabled) {
//        preferences.edit().putBoolean(CheckKey.CH_SOUND_ENABLED, sound_enabled).apply();
//    }

    public boolean getSound_enabled() {
        return preferences.getBoolean(CheckKey.CH_SOUND_ENABLED, true);
    }

//    public void setVibro_enabled(boolean vibro_enabled) {
//        preferences.edit().putBoolean(CheckKey.CH_VIBRO_ENABLED, vibro_enabled).apply();
//    }

    public boolean getVibro_enabled() {
        return preferences.getBoolean(CheckKey.CH_VIBRO_ENABLED, true);
    }

//    public void setSoundlevel(String soundlevel) {
//        preferences.edit().putString(CheckKey.CH_SOUNDLEVEL, soundlevel).apply();
//    }
//


//    public void setVibrationlevel(int vibrationlevel) {
//        preferences.edit().putInt(CheckKey.CH_VIBRATIONLEVEL, vibrationlevel).apply();
//    }

    public int getSoundLevelPercent(AudioManager audioManager) {
        int progress = preferences.getInt(CheckKey.CH_SOUNDLEVEL, -1);
        if (progress < 0) {
            int minVolume = 0;
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                minVolume = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
            }
            //int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            //int defaultVolume = 8;
            int defaultVolume = maxVolume / 2;//half of max

            progress = (defaultVolume - minVolume) * 100 / (maxVolume - minVolume);
            preferences.edit().putInt(CheckKey.CH_SOUNDLEVEL, progress).apply();
        }
        return progress;
    }

    public int calculateSoundLevel(AudioManager audioManager, int seekBarProgress) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int minVolume = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            minVolume = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        }
        int soundLevel = minVolume + (seekBarProgress * (maxVolume - minVolume) / 100);
        DLog.d("@@@@@@ Vibration Level: " + soundLevel);
        return soundLevel;
    }

    public int getVibrationlevelProgress() {
        return preferences.getInt(CheckKey.CH_VIBRATIONLEVEL, calculateSeekBarProgress(100));
    }

    private static int calculateSeekBarProgress(int vibrationLevel) {
        int pr = (vibrationLevel - minVibrationLevel) * 100 / (maxVibrationLevel - minVibrationLevel);
        return pr;
    }

    public int calculateVibrationLevel(int seekBarProgress) {
        // Рассчитываем уровень вибрации на основе прогресса seekBar (предполагаем, что progress идет от 0 до 100)
        int vibrationLevel = minVibrationLevel + (seekBarProgress * (maxVibrationLevel - minVibrationLevel) / 100);
        DLog.d("@@@@@@ Vibration Level: " + vibrationLevel);

        return vibrationLevel;
    }

//    public void setSound_type(int sound_type) {
//        preferences.edit().putInt(CheckKey.CH_SOUND_TYPE, sound_type).apply();
//    }

    public int getSound_type() {
        return preferences.getInt(CheckKey.CH_SOUND_TYPE, 0);
    }

    public boolean saveSettings(int soundType, boolean sound_enabled, int soundLevel, int sound_type, boolean isVibrationEnabled, int vibrationLevel) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(KEY_CURRENT_RAW, soundType)
                .putBoolean(CheckKey.CH_SOUND_ENABLED, sound_enabled)
                .putInt(CheckKey.CH_SOUNDLEVEL, soundLevel)
                .putInt(CheckKey.CH_SOUND_TYPE, sound_type)
                .putBoolean(CheckKey.CH_VIBRO_ENABLED, isVibrationEnabled)
                .putInt(CheckKey.CH_VIBRATIONLEVEL, vibrationLevel)
                .apply();
        return editor.commit();
    }


    private static class CheckKey {
        public static final String CH_SOUND_ENABLED = "CiEnq7mVQLBzDwNtTNWExw==";
        public static final String CH_VIBRO_ENABLED = "yvgik6lpdojb4VRzfyXebw==";
        public static final String CH_SOUNDLEVEL = "KANfDfijjbg6POg==";
        public static final String CH_VIBRATIONLEVEL = "TiDU09jJDx+7Ag==";
        public static final String CH_SOUND_TYPE = "ja0RaN1zrWwuRswHPXgwtQ==";

    }


}
