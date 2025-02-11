package com.demo.scrapper;

import static com.example.incomingphone.accessibility.ServiceSettingsActivity.SettingsFragment.pref_project;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.demo.scrapper.ytshorts.downloader.DownloadManagerPresenter;
import com.example.incomingphone.accessibility.AutoLoaderAccessibilityService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.scrapper.ui.main.SectionsPagerAdapter;
import com.demo.scrapper.databinding.ActivityMainBinding;
import com.walhalla.ui.DLog;


public class MainActivity extends AppCompatActivity implements DownloadManagerPresenter.View {


    private final Class<? extends AccessibilityService> clazz = AutoLoaderAccessibilityService.class;


    private ActivityMainBinding binding;
    private DownloadManagerPresenter presenter;
    ActivityResultLauncher<Intent> storageActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    o -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            //Android is 11 (R) or above
                            if (Environment.isExternalStorageManager()) {
                                //Manage External Storage Permissions Granted
                                DLog.d("onActivityResult: Manage External Storage Permissions Granted");
                            } else {
                                Toast.makeText(this, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Below android 11

                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler mHandler = new Handler(Looper.getMainLooper());

        presenter = new DownloadManagerPresenter(this, mHandler, this, storageActivityResultLauncher);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!SdkUtils.isAccessibilityOn(this, clazz)) {
            DLog.d("Navigate to Accessibility.");
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(view -> {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
            if (presenter != null) {
                boolean m = presenter.isNeedGrantPermission();
                if (m) {
                } else {
                    presenter.test0(MainActivity.this);
                }
                DLog.d("# isNeedGrantPermission: " + m);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //showCurrentConfiguration();

        //registerMyReceiver();
        if (SdkUtils.isAccessibilityOn(this, clazz)) {
            binding.title.setText("{OK}" + Build.VERSION.SDK_INT);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String project = sharedPreferences.getString(pref_project, null);
            //MyProject mm = Pinterests.getProjectByName(project);
            //launchTargetApp(this);
        }
    }

    @Override
    public void updateGUI() {

    }

    @Override
    public void showPermission33SnackBar() {

    }

    @Override
    public void showMessage(String s) {
        DLog.d(s);
        binding.log.setText(binding.log.getText()+s);
        setTitle(""+s);
    }
}