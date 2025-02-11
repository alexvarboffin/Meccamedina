package com.walhalla.meccamedina.activity.about

import com.walhalla.ui.R as uiR

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.walhalla.meccamedina.activity.splash.AppCompatPreferenceActivity
import com.walhalla.ui.DLog
import com.walhalla.ui.UConst
import com.walhalla.ui.plugins.Launcher
import com.walhalla.ui.plugins.Module_U

//import com.lb.material_preferences_library.PreferenceActivity;
//import com.lb.material_preferences_library.custom_preferences.Preference;
class ActivityAbout : AppCompatPreferenceActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
//        if (itemId == R.id.action_exit) {
//            this.finish();
//            return true;
//        } else
        if (itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme.Dark);
        super.onCreate(savedInstanceState)
        setContentView(com.walhalla.meccamedina.R.layout.activity_preference_list_content)
        val toolbar = findViewById<Toolbar>(com.walhalla.meccamedina.R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)

        val container = com.walhalla.meccamedina.R.id.b_container
        val root = findViewById<View>(container)
        if (root != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.

            if (savedInstanceState != null) {
                return
            }

            val mainPreferenceFragment = MainPreferenceFragment()
            supportFragmentManager.beginTransaction() //.addToBackStack(null)
                .replace(container, mainPreferenceFragment)
                .commit()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        //oveRidePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menu.add(Menu.NONE, R.string.action_exit, Menu.NONE, R.string.action_exit);
        return true
    }


    class MainPreferenceFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
        private val KEY_PREF_DEVELOPER_SUMMARY = "pref_developer_summary"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(com.walhalla.meccamedina.R.xml.pref_about)

            val preference2 =
                preferenceManager.findPreference<Preference>(KEY_PREF_DEVELOPER_SUMMARY)
            if (preference2 != null) {
//                String pubDescription = "Walhalla Dynamics\n(Click to Open Developer Page on Google Play)";
//                preference2.setSummary(pubDescription);
//                preference2.setOnPreferenceClickListener(this);

                preference2.summary = getString(uiR.string.play_google_pub)
            }


            val p2 = preferenceManager.findPreference<Preference>("key_pref_version")
            if (p2 != null && activity != null) {
                p2.summary = DLog.getAppVersion(activity)
            }


            val prefAbout = preferenceScreen.findPreference<Preference>("key_pref_about")
            prefAbout?.setSummary(com.walhalla.meccamedina.R.string.app_name)

            val preference = preferenceScreen.findPreference<Preference>(
                PREF_RATE_REVIEW_KEY
            )
            if (preference != null) {
                preference.onPreferenceClickListener = this
            }

            val preference1 = findPreference<Preference>(PREF_SHARE_KEY)
            if (preference1 != null) {
                preference1.onPreferenceClickListener = this
            }

            val nn = findPreference<Preference>(PREF_PRIVACY_POLICY)
            if (nn != null) {
                nn.onPreferenceClickListener = this
            }
        }


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        }

        override fun onPreferenceChange(preference: Preference, o: Any): Boolean {
            return false
        }

        override fun onPreferenceClick(value: Preference): Boolean {
            val packageName = activity?.packageName

            if (PREF_SHARE_KEY == value.key) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(com.walhalla.meccamedina.R.string.share_subject)
                )
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT, ("""
     ${getString(com.walhalla.meccamedina.R.string.share_message)}
     ${UConst.MARKET_CONSTANT}$packageName
     """.trimIndent())
                )
                startActivity(
                    Intent.createChooser(
                        shareIntent,
                        getString(com.walhalla.meccamedina.R.string.share_to)
                    )
                )
            } else if (PREF_RATE_REVIEW_KEY == value.key) {
                val intent = Intent("android.intent.action.VIEW")
                intent.setData(Uri.parse(UConst.MARKET_CONSTANT + packageName))
                startActivity(intent)
            } else if (PREF_PRIVACY_POLICY == value.key) {
                if (activity != null) {
                    Launcher.openBrowser(
                        activity,
                        getString(com.walhalla.meccamedina.R.string.url_privacy_policy)
                    )
                }
            } else if (KEY_PREF_DEVELOPER_SUMMARY == value.key) {
                if (activity != null) {
                    Module_U.moreApp(activity)
                }
            }
            return true
        }
    }

    companion object {
        private const val PREF_PRIVACY_POLICY = "pref_privacy_policy"
        private const val PREF_SHARE_KEY = "pref_share_key"
        private const val PREF_RATE_REVIEW_KEY = "pref_rate_review_key"
    }
}
