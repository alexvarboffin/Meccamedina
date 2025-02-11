package com.walhalla.meccamedina.activity.main

import ai.meccamedinatv.mekkalive.online.config.Const
import ai.meccamedinatv.mekkalive.online.mvp.presenter.HomePresenter
import android.Manifest
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.PowerManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.walhalla.library.activity.GDPR

import com.walhalla.meccamedina.R
import com.walhalla.meccamedina.activity.about.ActivityAbout
import com.walhalla.meccamedina.databinding.ActivityHomeBinding
import com.walhalla.meccamedina.fragment.Tasbeeh
import com.walhalla.ytlib.ui.fragments.common.BaseFragment
import com.walhalla.ui.DLog
import com.walhalla.ui.observer.RateAppModule
import com.walhalla.ui.plugins.Launcher
import com.walhalla.ui.plugins.Module_U
import com.walhalla.ytlib.BuildConfig
import com.walhalla.ytlib.domen.ListEntryUI
import com.walhalla.ytlib.home.HomeView
import com.walhalla.ytlib.repository.Const.LANDSCAPE_VIDEO_PADDING_DP
import com.walhalla.ytlib.repository.Const.PREF_ACCOUNT_NAME
import com.walhalla.ytlib.repository.Const.RECOVERY_DIALOG_REQUEST
import com.walhalla.ytlib.repository.Const.REQUEST_ACCOUNT_PICKER
import com.walhalla.ytlib.repository.Const.REQUEST_AUTHORIZATION
import com.walhalla.ytlib.repository.Const.REQUEST_GOOGLE_PLAY_SERVICES
import com.walhalla.ytlib.repository.Const.REQUEST_PERMISSION_GET_ACCOUNTS_
import com.walhalla.ytlib.repository.Const.setLayoutSize
import com.walhalla.ytlib.ui.ChannelVideosCallback
import com.walhalla.ytlib.ui.FragmentChannelVideos
import com.walhalla.ytlib.ui.fragments.VideoFragment
import com.walhalla.ytlib.utils.FileUtil
import com.walhalla.ytlib.utils.startRegistrationService
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks


//import com.google.android.youtube.player.YouTubeApiServiceUtil;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
class HomeActivity : MvpAppCompatActivity(), HomeView, BaseFragment.Callback,
    ChannelVideosCallback, PermissionCallbacks, VideoFragment.VideoFragmentCallback {
    private var mWakeLock: PowerManager.WakeLock? = null

    private var decorView: View? = null
    private var frmLayoutList: FrameLayout? = null
    private var isFullscreen = false

    private var drawer: Drawer? = null

    private var mVideoFragment: VideoFragment? = null

    private var mToolbar: Toolbar? = null

    @InjectPresenter
    lateinit var presenter: HomePresenter

//    @Inject
//    lateinit var presenter: HomePresenter
//    @ProvidePresenter
//    fun providePresenter(): HomePresenter = presenter

    private var rateAppModule: RateAppModule? = null

    private var credential: GoogleAccountCredential? = null
    private var binding: ActivityHomeBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
//        Thread.setDefaultUncaughtExceptionHandler(
//                CustomExceptionHandler.getInstance(getApplicationContext()));
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val gdpr = GDPR()
        gdpr.init(this)
        this.loadBannerAd()

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "app:mywakelocktag")
        mWakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)

        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Calling YouTube Data API ...")

        this.frmLayoutList = findViewById(R.id.container)
        this.mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(this.mToolbar)

        this.decorView = window.decorView

        //@@@ this.mVideoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        this.mVideoFragment =
            supportFragmentManager.findFragmentById(R.id.video_fragment_container) as VideoFragment?

        if (savedInstanceState == null) {
            drawer?.setSelection(0, false)
            //checkYouTubeApi(this);
            startRegistrationService(this)
        }
//        this.mToolbar.post(() -> {
//            Module_U.checkUpdate(getApplicationContext());
//        });
        rateAppModule = RateAppModule(this)
        lifecycle.addObserver(rateAppModule!!) //WhiteScree

        //replaceFragment(new Tasbeeh());
    }

    override fun showData(arr: List<ListEntryUI>) {
        //none
    }

    override fun showError(error: Int) {
    }

    override fun showError(tag: String, err: String) {
        //Toast.makeText(this, "@@" + err, Toast.LENGTH_SHORT).show();
        DLog.d("Exception->$tag-->$err")
    }

    override fun getVideoRequest(yt_entry: Array<String>, position: Int) {
        DLog.d("@@@@" + yt_entry.contentToString())
    }


    override fun showDrawerMenuList(entries: List<ListEntryUI>) {
        DLog.d("@@@@" + entries.size)

        //int type = entries.get(mSelectedDrawerItem).type;
        //String id = entries.get(mSelectedDrawerItem).id;
        val items = arrayOfNulls<PrimaryDrawerItem>(entries.size)
        for (i in entries.indices) {
            items[i] = PrimaryDrawerItem()
                .withName(entries[i].title.toString())
                .withIdentifier(i.toLong()).withSelectable(false)
        }

        val ind01 = (entries.size - 1).toLong()
        //long indQuibla = ind01 - 1;
        val indTasbeeh = ind01 - 2
        val indReadQuran = ind01 - 3

        //long indIslamicQuotes = ind01 - 4;
        try {
            drawer = DrawerBuilder(this@HomeActivity)
                .withAccountHeader(
                    AccountHeaderBuilder()
                        .withActivity(this@HomeActivity)
                        .withHeaderBackground(R.drawable.side_nav_bar)
                        .build()
                )
                .withActivity(this@HomeActivity)
                .withDisplayBelowStatusBar(true)
                .withToolbar(mToolbar!!)
                .withActionBarDrawerToggleAnimated(true) //@@@.withSavedInstance(savedInstanceState)
                .addDrawerItems(*items)
                .addStickyDrawerItems(
                    SecondaryDrawerItem()
                        .withName(R.string.read_quran)
                        .withIdentifier(indReadQuran)
                        .withSelectable(false),

                    SecondaryDrawerItem()
                        .withName(R.string.title_tasbeeh)
                        .withIdentifier(indTasbeeh)
                        .withSelectable(false),
                    //                            new SecondaryDrawerItem()
                    //                                    .withName("Muslim Helper: Prayer & Qibla")
                    //                                    .withIdentifier(indQuibla)
                    //                                    .withSelectable(false),
                    //                            new SecondaryDrawerItem()
                    //                                    .withName("Islamic Quotes")
                    //                                    .withIdentifier(indIslamicQuotes)
                    //                                    .withSelectable(false),


                    SecondaryDrawerItem()
                        .withName(
                            (getString(R.string.action_about) + " "
                                    + DLog.getAppVersion(this@HomeActivity))
                        )
                        .withIdentifier(ind01)
                        .withSelectable(false)
                )
                .withOnDrawerItemClickListener { view: View?, position: Int, drawerItem: IDrawerItem<*, *>? ->
                    var position = position
                    position = position
                    if (drawerItem != null) {
                        DLog.d("[Selected menu item]" + position + " " + drawerItem.identifier)

                        if (drawerItem.identifier >= 0 && position != -1) {
                            setToolbarAndSelectedDrawerItem(
                                entries[position - 1].title, position - 1
                            )

                            //Navigation
                            //int video_type = entries.get(position - 1).type;
                            //String video_id = entries.get(position - 1).id;
                            presenter.onMenuItemSelected(position - 1)
                        } else if (position == -1) {
                            if (indTasbeeh == drawerItem.identifier) {
                                replaceFragment0(Tasbeeh())
                            } else if (indReadQuran == drawerItem.identifier) {
                                FileUtil.copyReadAssets(this@HomeActivity)
                            } else if (ind01 == drawerItem.identifier) {
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        ActivityAbout::class.java
                                    )
                                )
                                overridePendingTransition(
                                    R.anim.open_next,
                                    R.anim.close_main
                                )
                            }
                        }
                    }
                    false
                } //.withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build()
        } catch (e: Exception) {
            DLog.handleException(e)
        }


        if (!entries.isEmpty()) {
            setToolbarAndSelectedDrawerItem(entries[0].title, 0)
        }

        supportFragmentManager
            .addOnBackStackChangedListener {
                DLog.d("@@@")
                if (binding!!.container != null) {
                    //updateTitleAndDrawer(fragment);
                    //if (mFragment.getClass().getName().equals(FragmentChannelVideos.class.getName())) {
                    //@@@setToolbarAndSelectedDrawerItem(entries.get(position).title, position);
                    //}
                }
            }
    }


    override fun renderVideoList(list: List<ListEntryUI>, position: Int) {
        if (Const.KEY_RENDER_VIDEO_LIST) {
            val mFragment: FragmentChannelVideos
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment != null) {
                if (currentFragment is FragmentChannelVideos) {
                    mFragment = currentFragment
                    mFragment.swapRecyclerView(list, position)
                }
            } else {
                mFragment = FragmentChannelVideos()
                mFragment.swapRecyclerView(list, position)
            }
        }
    }

    override fun showVideoFragment(type: Int, id: String) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment != null) {
            if (currentFragment is FragmentChannelVideos) {
                //mFragment = (FragmentChannelVideos) currentFragment;
                onVideoSelected(id)
                return
            }
        }

        val mFragment = FragmentChannelVideos()
        //        Bundle bundle = new Bundle();
//        bundle.putInt(Utils.TAG_VIDEO_TYPE, type);
//        bundle.putString(Utils.TAG_CHANNEL_ID, id);
//        mFragment.setArguments(bundle);
        replaceFragment0(mFragment)
        onVideoSelected(id)
        DLog.d("@@@@@@@@@@@$currentFragment")
    }

    override fun startActivityForResult(intent: Intent, requestAuthorization: Int) {
    }

    override fun onVideoSelected(video_id: String) {
        //@@@ this.mVideoFragment = getFragmentManager().findFragmentById(R.id.video_fragment_container);
        val fragment = supportFragmentManager.findFragmentById(R.id.video_fragment_container)
        if (fragment is VideoFragment) {
            DLog.d("Selected Video $video_id")
            fragment.setVideoId(video_id)
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun haveResultView() {
    }

    override fun retryView() {
    }

    override fun noResultView() {
    }

    override fun onVideoItemSelected(s: String) {
        onVideoSelected(s)
    }

    override fun setCustomLoadMoreView(view_id: Int) {
    }

    override fun swapRecyclerView(arr: List<ListEntryUI>, position: Int) {
    }


    fun replaceFragment0(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//        ft.replace(R.id.container, new Tasbeeh());
//        //ft.addToBackStack(null);
//        ft.commit();
    }


    private fun setToolbarAndSelectedDrawerItem(title: String, selectedDrawerItem: Int) {
        mToolbar!!.title = title
        if (drawer != null) {
            drawer!!.setSelection(selectedDrawerItem.toLong(), false)
        }
    }

    //    @Override
    //    public void onFullscreen(boolean isFullscreen) {
    //        this.isFullscreen = isFullscreen;
    //        layout0(null);
    //    }


    override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
        this.isFullscreen = true
        layout00(fullscreenView)
    }

    override fun onExitFullscreen() {
        this.isFullscreen = false
        layout00(null)
    }

    @SuppressLint("WrongConstant", "SourceLockedOrientationActivity")
    private fun layout00(fullscreenView: View?) {
        //ver1

        val isPortrait = resources.configuration.orientation == 1

        //boolean isPortrait = !this.isFullscreen;
        //DLog.d("@@@ isFullscreen " + this.isFullscreen + " " + isPortrait);
        if (this.isFullscreen) {
            mToolbar!!.visibility = View.GONE
            frmLayoutList!!.visibility = View.GONE
            decorView!!.systemUiVisibility = 2
            cancelBannerLoading()

            val m = mVideoFragment!!.view
            if (m != null) {
                setLayoutSize(m, -1, -1)
                mVideoFragment!!.onEnterFullScreen(fullscreenView)
                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }
        } else if (isPortrait) {
            mToolbar!!.visibility = 0
            frmLayoutList!!.visibility = 0
            resumeBannerLoading()

            val m = mVideoFragment!!.view
            if (m != null) {
                setLayoutSize(m, -2, -2)
                mVideoFragment!!.onExitFullscreen()
                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }
        } else {
            //fullscreen false
            //portraite false

            mToolbar!!.visibility = 0
            frmLayoutList!!.visibility = 0
            resumeBannerLoading()
            //Toast.makeText(this, "@w@" + isPortrait + "@" + isFullscreen, Toast.LENGTH_LONG).show();
            val screenWidth = dpToPx(resources.configuration.screenWidthDp)
            val m = mVideoFragment!!.view
            if (m != null) {
                setLayoutSize(
                    m, (screenWidth - (screenWidth / 4)) - dpToPx(
                        LANDSCAPE_VIDEO_PADDING_DP
                    ), -2
                )
                mVideoFragment!!.onExitFullscreen()
                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }
        }
    }

    private fun loadBannerAd() {
        // Start loading the ad in the background.
        //removed  this.binding.adView.loadAd(new AdRequest.Builder().build());
        //removed  //Toast.makeText(this, "@@@@" + isFullscreen, Toast.LENGTH_LONG).show();
        //removed  this.binding.adView.setVisibility(View.VISIBLE);
        //removed  if (BuildConfig.DEBUG) {
        //removed      Toast.makeText(this, "binding.adView load", Toast.LENGTH_SHORT).show();
        //removed  }
    }

    private fun cancelBannerLoading() {
        //removed binding.adView.setVisibility(View.GONE);
        //removed binding.adView.pause(); // Остановка загрузки, чтобы не показывать новые рекламные объявления
        //removed DLog.d("{binding.adView canceled}");
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "binding.adView canceled", Toast.LENGTH_SHORT).show()
        }
    }

    // Метод для возобновления загрузки баннера
    private fun resumeBannerLoading() {
        //removed  binding.adView.resume(); // Возобновление загрузки, чтобы показывать новые рекламные объявления
        //removed  binding.adView.setVisibility(View.VISIBLE);
        //removed  if (BuildConfig.DEBUG) {
        //removed      Toast.makeText(this, "resumeBanner", Toast.LENGTH_SHORT).show();
        //removed  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            DLog.d(if ((data.action == null)) "..." else data.action + "\t" + requestCode)
        }

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
//                // Do something after user returned from app settings screen, like showing a Toast.
//                Toast.makeText(this, R.string.returned_from_app_settings_to_activity,
//                                Toast.LENGTH_SHORT)
//                        .show();
        } else if (requestCode == RECOVERY_DIALOG_REQUEST) {
            recreate()
            //Autorization
        } else if (requestCode == REQUEST_GOOGLE_PLAY_SERVICES) {
            if (resultCode != RESULT_OK) {
                errorResult(
                    "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app."
                )
            } else {
                if (resultCode == RESULT_OK) {
                    //....
                }
                presenter.getResultsFromApi(this)
            }
        } else if (requestCode == REQUEST_ACCOUNT_PICKER) {
            if (resultCode == RESULT_OK && data != null && data.extras != null) {
                val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                if (accountName != null) {
                    val settings = getPreferences(MODE_PRIVATE)
                    val editor = settings.edit()
                    editor.putString(PREF_ACCOUNT_NAME, accountName)
                    editor.apply()
                    presenter.setSelectedAccountName(accountName)
                    presenter.getResultsFromApi(this)
                }
            }
        } else if (requestCode == REQUEST_AUTHORIZATION) {
            DLog.d("0000000000000")
            if (resultCode == RESULT_OK) {
                presenter.getResultsFromApi(this)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        DLog.d("@@@$newConfig")

        //@@@@@@@@@ layout();
    }

    override fun onClickSelectedVideo() {
        presenter.videoDataClick
    }

    //RecyclerView var0
    override fun onItemClick(parent: RecyclerView, clickedView: View, position: Int) {
        presenter.onVideoItemSelected(position)
    }

    override fun onItemLongClick(parent: RecyclerView, clickedView: View, position: Int) {
    }


    private fun dpToPx(dp: Int): Int {
        return (((dp.toFloat()) * resources.displayMetrics.density) + 0.5f).toInt()
    }

    /**
     * Override the default implementation when the user presses the back key.
     */
    override fun onBackPressed() {
        //super.onBackPressed();
        if (this.isFullscreen) {
            mVideoFragment!!.backnormal()
        } else {
            //destroy -> super.onBackPressed();
            // Move the task containing the MainActivity to the back of the activity stack, instead of
            // destroying it. Therefore, MainActivity will be shown when the user switches back to the app.
            moveTaskToBack(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        if (BuildConfig.DEBUG) {
//            menu.add("Crash")
//                    .setOnMenuItemClickListener(v -> {
//                        throw new RuntimeException("@@ Test Crash @@");
//                    });
//        }
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_resume) {
            item.setEnabled(false)
            presenter.videoDataClick
            item.setEnabled(true)
            return true
        } else if (itemId == R.id.action_about) {
            //Module_U.aboutDialog(this);
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            overridePendingTransition(R.anim.open_next, R.anim.close_main)
            return true
        } else if (itemId == R.id.action_privacy_policy) {
            Launcher.openBrowser(this, getString(R.string.url_privacy_policy))
            return true
        } else if (itemId == R.id.action_rate_app) {
            Launcher.rateUs(this)
            return true
        } else if (itemId == R.id.action_share_app) {
            Module_U.shareThisApp(this)
            return true
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this)
            return true
        }
        return super.onOptionsItemSelected(item)

//            case R.string.start_test_again:
//                return false;

//            case R.id.action_discover_more_app:
//                Module_U.moreApp(this);
//                return true;
//
//            case R.id.action_exit:
//                this.finish();
//                return true;
        //action_how_to_use_app
        //action_support_developer

        //return super.onOptionsItemSelected(item);
    }

    override fun fragmentConnected() {
        presenter.fragmentConnected()
    }


    override fun loadMore(itemsCount: Int, maxLastVisiblePosition: Int) {
//        mIsStillLoading = false;
//        if (mIsStillLoading) {
//            setCustomLoadMoreView(R.layout.loadmore_progressbar);
//
//            new Handler().postDelayed(() -> {
//                onClickSelectedVideo();
//            }, 1000);
//            return;
//        }
        disableLoadMore("111")
    }

    override fun disableLoadMore(name: String) {
//        Log.d(TAG, "disableLoadMore: " + name + "----------------------------");
//        this.mIsStillLoading = false;
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment != null) {
            if (currentFragment is FragmentChannelVideos) {
                currentFragment.disableLoadMore()
            }
        }
    }


    public override fun onPause() {
        //removed   if (binding.adView != null) {
        //removed       binding.adView.pause();
        //removed   }
        super.onPause()
        if (mWakeLock!!.isHeld) {
            mWakeLock!!.release()
        }
    }

    public override fun onResume() {
        super.onResume()
//        if (binding.adView != null) {
//            binding.adView.resume();
//        }
    }

    public override fun onDestroy() {
        //removed    if (binding.adView != null) {
        //removed        binding.adView.destroy();
        //removed    }
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
        super.onDestroy()
    }


    override fun errorResult(s: String?) {
        DLog.d("@w@$s")
        val mm: List<ListEntryUI> = ArrayList()
        showDrawerMenuList(mm)
    }

    override fun successResult(output: String?) {
        DLog.d("@$output")
    }

    private var progressDialog: ProgressDialog? = null

    override fun chooseAccount(mCredential: GoogleAccountCredential?) {
        this.credential = mCredential
        fff0()
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS_)
    private fun fff0() {
        val perms = arrayOf(
            Manifest.permission.GET_ACCOUNTS
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing

            val accountName = getPreferences(MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null)
            if (accountName != null) {
                DLog.d("@@[chooseAccount]@1@")
                credential!!.setSelectedAccountName(accountName)
                presenter.getResultsFromApi(this)
            } else {
                DLog.d("@@[chooseAccount]@@")
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                    credential!!.newChooseAccountIntent(),
                    REQUEST_ACCOUNT_PICKER
                )
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            DLog.d("@@[chooseAccount]@0@")
            // Do not have permissions, request them now
            //This app needs to access your Google account (via Contacts).
            EasyPermissions.requestPermissions(
                this, getString(R.string.confirm_action),
                REQUEST_PERMISSION_GET_ACCOUNTS_, *perms
            )
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     * requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * AdvertCallback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     * permission
     * @param list        The requested permission list. Never null.
     */
    override fun onPermissionsGranted(requestCode: Int, list: List<String>) {
        DLog.d("[Granted]$requestCode $list")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        DLog.d("{@@}$requestCode $perms")
        if (REQUEST_PERMISSION_GET_ACCOUNTS_ == requestCode) {
            Toast.makeText(this, R.string.confirm_action, Toast.LENGTH_LONG).show()
            // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
            // This will display a dialog directing them to enable the permission in app settings.
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                AppSettingsDialog.Builder(this).build().show()
            }
        }
    }

    override fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        //final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            val dialog = apiAvailability.getErrorDialog(
                this, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES
            )
            dialog?.show()
        }
    }


    override fun progressDialogHide() {
        if (progressDialog != null) {
            progressDialog!!.hide()
        }
    }

    override fun progressDialogShow() {
        progressDialog!!.show()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        if (rateAppModule != null) {
            rateAppModule!!.appReloadedHandler()
        }
        super.onSaveInstanceState(outState)
    }

    override fun errorResponse(e: Exception) {
        DLog.handleException(e)
        //@@@ mPresenter.errorResponse(e);
    }

    //    private void checkYouTubeApi(Activity context) {
    //        YouTubeInitializationResult errorReason = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context);
    //        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    //        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
    //
    //        DLog.d("[+] YOUTUBE --> " + errorReason.toString() + "@");
    //        DLog.d("[+] GOOGLE PLAY --> " + connectionStatusCode + "@");
    //
    //        if (YouTubeInitializationResult.SERVICE_MISSING.equals(errorReason)) {
    //            DLog.d("===============================================");
    //        } else if (errorReason.isUserRecoverableError()) {
    //            errorReason.getErrorDialog(context, 123).show();
    //
    ////            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
    ////            int result = googleAPI.isGooglePlayServicesAvailable(this);
    ////            if(result != ConnectionResult.SUCCESS) {
    ////                if(googleAPI.isUserResolvableError(result)) {
    ////                    googleAPI.getErrorDialog(this, result,
    ////                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
    ////                }
    ////
    ////                return false;
    ////            }
    //
    //        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
    //            Toast.makeText(context, String.format(context.getString(R.string.error_player),
    //                    errorReason), Toast.LENGTH_LONG).show();
    //        }
    //    }
    @SuppressLint("PackageManagerGetSignatures")
    override fun onInitializationFailure(errorReason: PlayerConstants.PlayerError) {
        val name: String = errorReason.name
        if (BuildConfig.DEBUG) {
            DLog.d("@$name")
        }
        if ("VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER" != name) {
            Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
        }
    }
}
