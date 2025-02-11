package ai.meccamedinatv.mekkalive.online.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.walhalla.ui.DLog;

import ai.meccamedinatv.mekkalive.online.R;
import com.walhalla.ytlib.TasbeehPreferences;

import ai.meccamedinatv.mekkalive.online.databinding.DialogSoundSettingsBinding;
import ai.meccamedinatv.mekkalive.online.databinding.FragmentTasbeehBinding;

public class Tasbeeh extends Fragment {


    int counter;


    private FragmentTasbeehBinding binding;

    private SoundPool soundPool;
    private final int[] burps = new int[3];
    private TasbeehPreferences pref;
    private AudioManager audioManager;

    //private MediaPlayer player = new MediaPlayer();


//    public void updateDisplay() {
//        this.display.setText("Your total is: " + this.counter);
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity a = getActivity();
        this.pref = new TasbeehPreferences(getActivity());
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        burps[0] = soundPool.load(a.getApplicationContext(), R.raw.beep, 1);
        burps[1] = soundPool.load(a.getApplicationContext(), R.raw.kran, 1);
        burps[2] = soundPool.load(a.getApplicationContext(), R.raw.onetime, 1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTasbeehBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint({"WakelockTimeout", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getSupportActionBar ().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.customactionbar);

        binding.reset.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.5f);
            } else if (action == MotionEvent.ACTION_UP) {
                v.setAlpha(1.0f);
                pref.preferencesTasbeeh(0);
                counter = 0;
                binding.tvDisplay.setText("" + counter);
            }
            return true;
        });


        binding.r0.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.5f);
            } else if (action == MotionEvent.ACTION_UP) {
                v.setAlpha(1.0f);
                if (counter > 0) {
                    counter--;
                    pref.preferencesTasbeeh(counter);
                    binding.tvDisplay.setText("" + counter);
                }
            }
            return true;
        });

        binding.settings.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.5f);
            } else if (action == MotionEvent.ACTION_UP) {
                v.setAlpha(1.0f);
                showSoundSettingsDialog(getContext());
            }
            return true;
        });

        //binding.save.setOnTouchListener(this);

        counter = pref.preferencesTasbeeh();
        binding.tvDisplay.setText("" + counter);

//        binding.save.setOnClickListener(v -> {
//            Uqqq.preferencesTasbeeh(counter, getActivity());
//        });

        binding.start.setOnClickListener(v -> {
            adbbbd();
        });
        binding.imageView9.setOnClickListener(v -> {
            adbbbd();
        });
    }

    private void vibratePhone(int vibrationlevel) {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Вибрация на 100 миллисекунд
            DLog.d("@@@@"+vibrationlevel);
            vibrator.vibrate(vibrationlevel);
        }
    }

    private void adbbbd() {
        binding.pulsator.setCount(1);
        binding.pulsator.setDuration(600);
        binding.pulsator.start();
        counter++;
        animateDisplay(binding.tvDisplay);
        binding.tvDisplay.setText(String.valueOf(counter));
        pref.preferencesTasbeeh(counter);


        boolean soundEnabled = pref.getSound_enabled();

        if (soundEnabled) {
            int soundType = pref.getSound_type();
            //int soundLevel1 = pref.calculateSoundLevel(audioManager, progress);
            playSound(soundType);
        }

        boolean vibroEnabled = pref.getVibro_enabled();
        if (vibroEnabled) {
            int progress = pref.getVibrationlevelProgress();
            int vibrationlevel = pref.calculateVibrationLevel(progress);
            vibratePhone(vibrationlevel);
        }


    }

    private void animateDisplay(TextView display) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                display,
                PropertyValuesHolder.ofFloat("scaleX", 1.8f),
                PropertyValuesHolder.ofFloat("scaleY", 1.8f)
        );
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.RESTART);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }

//    Map<Integer, Integer> map = new HashMap<>();
//            map.put(1, R.id.radio_sound_signal);
//            map.put(2, R.id.radio_tap);
//            map.put(3, R.id.radio_one_time);

    private void showSoundSettingsDialog(Context context) {
        Dialog dialog = new Dialog(context);
        @NonNull DialogSoundSettingsBinding binding = DialogSoundSettingsBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());

        int currentRawSound = pref.loadCurrentRawSound();
        if (currentRawSound == 0) {
            binding.radioSoundSignal.setChecked(true);
        } else if (currentRawSound == 1) {
            binding.radioTap.setChecked(true);
        } else {
            binding.radioOneTime.setChecked(true);
        }

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.radio_sound_signal) {
//                playSound(R.raw.beep);
//            } else if (checkedId == R.id.radio_tap) {
//                playSound(R.raw.kran);
//            } else {
//                playSound(R.raw.onetime);
//            }

            if (checkedId == R.id.radio_sound_signal) {
                playSound(0);
            } else if (checkedId == R.id.radio_tap) {
                playSound(1);
            } else {
                playSound(2);
            }
        });

        final boolean soundEnabled = pref.getSound_enabled();
        final boolean vibroEnabled = pref.getVibro_enabled();

        binding.switchSound.setChecked(soundEnabled);
        binding.switchVibration.setChecked(vibroEnabled);
        //binding.seekbarVibration.setMax(400);

        int vibrationlevel = pref.getVibrationlevelProgress();
        binding.seekbarVibration.setProgress(vibrationlevel);


        int DEFAULT_SOUND_LEVEL0 = pref.getSoundLevelPercent(audioManager);
        binding.seekbarSound.setProgress(DEFAULT_SOUND_LEVEL0);

        handleContainer(binding.seekbarSound, binding.cSound, soundEnabled);
        handleVibroContainer(binding.seekbarVibration, vibroEnabled);


        binding.switchSound.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            handleContainer(binding.seekbarSound, binding.cSound, isChecked);
        });

        binding.switchVibration.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            handleVibroContainer(binding.seekbarVibration, isChecked);
        });


        binding.seekbarVibration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {

                    if (binding.switchVibration.isEnabled()) {
                        int vibrationlevel = pref.calculateVibrationLevel(progress);
//                        int m = calculateSeekBarProgress(vibrationlevel);
//                        DLog.d(vibrationlevel + " @ " + m + " @ " + progress);

                        vibratePhone(vibrationlevel);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //binding.seekbarSound.setMin();

        int minVolume = 0;
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            minVolume = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        }

        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        DLog.d("@@MAX_AUDIO@@" + minVolume + "@" + maxVolume + "@@@" + streamVolume);

        //@10@
        //binding.seekbarSound.setMax(maxVolume);

        binding.seekbarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {

                    int soundLevel1 = pref.calculateSoundLevel(audioManager, progress);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, soundLevel1, 0);

                    DLog.d("@@MAX_AUDIO@@" + maxVolume + "@@@" + streamVolume + "@@@" + progress);

                    if (binding.switchSound.isEnabled()) {
                        int selectedSoundTypeId = binding.radioGroup.getCheckedRadioButtonId();
                        int soundType = 0;
                        if (selectedSoundTypeId == R.id.radio_sound_signal) {
                            //R.raw.beep;
                            soundType = 0;
                        } else if (selectedSoundTypeId == R.id.radio_tap) {
                            //R.raw.kran;
                            soundType = 1;
                        } else {
                            //R.raw.onetime;
                            soundType = 2;
                        }
                        playSound(soundType);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Не нужно ничего делать
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Не нужно ничего делать
            }
        });
        //binding.seekbarVibration;


        binding.btnCancel.setOnClickListener(v -> {

            //reset to default
            int soundLevel = pref.calculateSoundLevel(audioManager, DEFAULT_SOUND_LEVEL0);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, soundLevel, 0);
            dialog.dismiss();
        });

        binding.btnSave.setOnClickListener(v -> {

            boolean isSoundEnabled = binding.switchSound.isChecked();
            int soundLevel9 = binding.seekbarSound.getProgress();
            boolean isVibrationEnabled = binding.switchVibration.isChecked();
            int vibrationLevel = binding.seekbarVibration.getProgress();


            int selectedSoundTypeId = binding.radioGroup.getCheckedRadioButtonId();
            int soundType = 0;
            if (selectedSoundTypeId == R.id.radio_sound_signal) {
                //R.raw.beep;
                soundType = 0;
            } else if (selectedSoundTypeId == R.id.radio_tap) {
                //R.raw.kran;
                soundType = 1;
            } else {
                //R.raw.onetime;
                soundType = 2;
            }

            pref.saveSettings(soundType, isSoundEnabled, soundLevel9, soundType, isVibrationEnabled, vibrationLevel);

            dialog.dismiss();
        });

        // Отобразить диалоговое окно
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    private void handleVibroContainer(SeekBar seekbarVibration, boolean isChecked) {
        seekbarVibration.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    private void handleContainer(SeekBar seekbarSound, LinearLayout cSound, boolean isChecked) {
        seekbarSound.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        cSound.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    private void playSound(int itemId) {
        soundPool.play(itemId + 1, 1, 1, 0, 0, 1);
    }


//    @Override
//    public void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        if (player != null) {
//            player.release();
//        }
//    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
//    @Override
//    public boolean performClick() {
//        super.performClick();
//        doSomething();
//        return true;
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        if (mInterstitialAd.isLoaded()) {
////            mInterstitialAd.show();
////        }
//    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (event.getKeyCode()) {
//                case KeyEvent.KEYCODE_VOLUME_UP:
//                    // Volume up key detected
//                    // Do something
//                    counter++;
//                    display.setText("" + counter);
////                    Toast.makeText(context, "Up", Toast.LENGTH_SHORT).show();
//                    return true;
//                case KeyEvent.KEYCODE_VOLUME_DOWN:
//                    // Volume down key detected
//                    // Do something
//                    if (counter != 0) {
//                        counter--;
//                        display.setText("" + counter);
//                    }
////                    Toast.makeText(context, "Down", Toast.LENGTH_SHORT).show();
//                    return true;
//            }
//        }
//
//        return super.dispatchKeyEvent(event);
//    }
}
