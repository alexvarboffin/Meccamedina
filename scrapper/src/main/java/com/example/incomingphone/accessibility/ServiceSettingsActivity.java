package com.example.incomingphone.accessibility;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.DropDownPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.demo.scrapper.R;

import com.example.incomingphone.promo.Pinterests;
import com.pinterest.PinterestUsers;
import com.pinterest.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame, SettingsFragment.newInstance())
                    .commit();
        }
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private static final String TAG = "@@@";
        private DropDownPreference boardName;
        public static String pref_dropdown = "dropdown";
        public static String pref_boardName = "boardName";
        public static String pref_project = "project";


        public static Fragment newInstance() {
//            rootKey: String = "root") = SettingsFragment().apply {
//
//                // When you pass a string argument with key ARG_PREFERENCE_ROOT,
//                // PreferenceFragmentCompat picks it up and supplies it as an argument to onCreatePreferences
//                arguments = Bundle().apply { putString(ARG_PREFERENCE_ROOT, rootKey) }
            return new SettingsFragment();
        }

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            // Using this method instead of addPreferencesFromXml so we can specify the root preference screen
            // This way, navigating to a new screen is as simple as calling SettingsFragment.newInstance("new_root")
            setPreferencesFromResource(R.xml.prefs, rootKey);

            PinterestUsers cfg = TextUtils1.readConfig(getActivity());
            List<User> users = cfg.getUsers();
            String[] entries = new String[users.size()];
            String[] entriesValues = new String[users.size()];
            Map<String, List<String>> boardmap = new HashMap<>();

            for (int i = 0; i < users.size(); i++) {
                entries[i] = users.get(i).getName();
                String key = users.get(i).getName();
                entriesValues[i] = key;
                List<String> m = users.get(i).getBoards();
                boardmap.put(key, m);
            }

            DropDownPreference project = findPreference(pref_project);
            if (project != null) {
                project.setEntries(Pinterests.pentries);
                project.setEntryValues(Pinterests.pentries);
            }


            SwitchPreferenceCompat compat = findPreference(getString(R.string.pref_youtube));

            DropDownPreference dropdown = findPreference(pref_dropdown);
            boardName = findPreference(pref_boardName);

            //android:entries="@array/entries" android:entryValues="@array/entry_values"

            if (dropdown != null) {


                dropdown.setEntries(entries);
                dropdown.setEntryValues(entriesValues);
                dropdown.setOnPreferenceChangeListener((preference, newValue) -> {
                    List<String> list = boardmap.get((String) newValue);
                    handleBoardName(list);
                    return true;
                });

                String summary = String.valueOf(dropdown.getSummary());
                Log.d(TAG, "onCreatePreferences: " + summary);

                if (!TextUtils.isEmpty(summary)) {
                    List<String> list = boardmap.get(summary);
                    handleBoardName(list);
                }
            }

        }

        private void handleBoardName(List<String> list) {
            if (list != null) {
                String[] entries1 = new String[list.size()];
                String[] entriesValues1 = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    entries1[i] = list.get(i);
                    entriesValues1[i] = list.get(i);
                }
                if (boardName != null) {
                    boardName.setEntries(entries1);
                    boardName.setEntryValues(entriesValues1);
                }
            }
        }
    }
}
