package com.walhalla.meccamedina

import ai.meccamedinatv.mekkalive.app.AppOpenAdManager
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase
import com.onesignal.OneSignal
import com.onesignal.OneSignal.initWithContext

import com.onesignal.debug.LogLevel
import com.walhalla.ui.DLog

class DexApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {
    private var appOpenAdManager: AppOpenAdManager? = null
    private var currentActivity: Activity? = null

    private var intersticialCounter = 0


    fun hasToDisplayIntersticiel(): Boolean {
        return this.intersticialCounter >= 3
    }

    fun incIntersticialCounter() {
        DLog.d("Counter " + this.intersticialCounter)
        intersticialCounter++
    }

    fun resetIntersticialCounter() {
        this.intersticialCounter = 0
    }

    val secondsBeforeIntersticial: Int
        get() = 5000


    //rts
    //    public void setConnectivityListener(NetworkStateReceiver.NetworkStateReceiverListener listener)
    //    {
    //        NetworkStateReceiver.networkStateReceiverListener = listener;
    //    }
    //Valley
    //    private RequestQueue mRequestQueue;
    //
    //    public ImageLoader getmImageLoader() {
    //        return mImageLoader;
    //    }
    //    private final ImageLoader mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
    //
    //        private final LruCache<String, Bitmap> cache = new LruCache<>(20);
    //
    //        @Override
    //        public Bitmap getBitmap(String url) {
    //            return this.cache.get(url);
    //        }
    //
    //        @Override
    //        public void putBitmap(String url, Bitmap bitmap) {
    //            this.cache.put(url, bitmap);
    //        }
    //    });
    //    public RequestQueue getRequestQueue() {
    //        return mRequestQueue;
    //    }
    override fun onCreate() {
        super<MultiDexApplication>.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("CrashHandler @@@", "Uncaught exception in thread: ${thread.name}", throwable)
        }



        this.registerActivityLifecycleCallbacks(this)
        FirebaseApp.initializeApp(this)


        var0 = this
        val list: MutableList<String> = ArrayList()
        if (!BuildConfig.DEBUG) {
            list.add(AdRequest.DEVICE_ID_EMULATOR)
            list.add("A8A2F7804653E219880030864C1F32E4")
            list.add("5D5A89BC6372A49242D138B9AC352894")
            list.add("A2A86E2966898F258CB671EE038C2703")
            list.add("8CAA90D84051B086BE2AE92278905B28")
            list.add("5B95ABDBD2832E0D3C07CE70E85A69EB")
        }
        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(list)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        MobileAds.initialize(
            this
        ) { initializationStatus: InitializationStatus? -> }

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        } catch (e: Exception) {
            DLog.handleException(e)
        }

        //Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler.getInstance(getApplicationContext()));
        //mRequestQueue = Volley.newRequestQueue(this);
        //Fabric.with(this, new Crashlytics());

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics.getInstance(this)

        //getKeyHash("SHA");
        //getKeyHash("MD5");

//        try {
////            FirebaseOptions options = new FirebaseOptions.Builder()
////                    .setApplicationId("1087151334766") //APP ID Required for Analytics.
////                    .setProjectId("1087151334766") //PROJECT ID Required for Firebase Installations.
////                    .setApiKey(getString(R.string.api_key)) // Required for Auth.
////                    .build();
////            com.google.firebase.FirebaseApp.initializeApp(this, options, getString(R.string.app_name));
//            com.google.firebase.FirebaseApp.initializeApp(this);
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
        if (!TextUtils.isEmpty(ONESIGNAL_APP_ID)) {
            // Enable verbose OneSignal logging to debug issues if needed.

//            if (BuildConfig.DEBUG) {
//                OneSignal.debug.logLevel = LogLevel.VERBOSE
//            }

            // OneSignal Initialization
//            OneSignal.initWithContext(this);
//            OneSignal.setAppId(ONESIGNAL_APP_ID);
            initWithContext(this, ONESIGNAL_APP_ID)

            //            // OneSignal Initialization
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    .unsubscribeWhenNotificationsAreDisabled(true)
//                    .autoPromptLocation(true)
//                    .init();
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        appOpenAdManager = AppOpenAdManager(getString(R.string.r1))
    }


    //    @SuppressLint("PackageManagerGetSignatures")
    //    private void getKeyHash(String hashStretagy) {
    //        PackageInfo info;
    //        try {
    //            info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
    //            for (Signature signature : info.signatures) {
    //                MessageDigest md;
    //                md = MessageDigest.getInstance(hashStretagy);
    //                md.update(signature.toByteArray());
    //
    //
    //                final byte[] digest = md.digest();
    //                final StringBuilder toRet = new StringBuilder();
    //                for (int i = 0; i < digest.length; i++) {
    //                    if (i != 0) {
    //                        //toRet.append(":");
    //                    }
    //                    int b = digest[i] & 0xff;
    //                    String hex = Integer.toHexString(b);
    //                    if (hex.length() == 1) toRet.append("0");
    //                    toRet.append(hex);
    //                }
    //
    //                DLog.d(getPackageName() + " || " + toRet);
    //                String something = new String(Base64.encode(md.digest(), 0));
    //                DLog.d("KeyHash  -->>>>>>>>>>>>" + something);
    //
    //                // Notification.registerGCM(this);
    //            }
    //        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e1) {
    //            DLog.handleException(e1);
    //        } catch (Exception e) {
    //            DLog.handleException(e);
    //        }
    //    }
    fun log(id: String?, name: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        FirebaseAnalytics.getInstance(this)
            .logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    /**
     * LifecycleObserver method that shows the app open ad when the app moves to foreground.
     */
    //    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    //    protected void onMoveToForeground() {
    //        // Show the ad (if available) when the app moves to foreground.
    //        appOpenAdManager.showAdIfAvailable(currentActivity);
    //    }
    override fun onStart(owner: LifecycleOwner) {
        // Show the ad (if available) when the app moves to foreground.
        if (ACTIVITY_MOVES_TO_FOREGROUND_HANDLE) {
            appOpenAdManager!!.showAdIfAvailable(currentActivity!!)
        }
    }

    /**
     * ActivityLifecycleCallback methods.
     */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
        // SDK or another activity class implemented by a third party mediation partner. Updating the
        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
        // one that shows the ad.
        if (!appOpenAdManager?.isShowingAd!!) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    /**
     * Shows an app open ad.
     *
     * @param activity                 the activity that shows the app open ad
     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    fun showAdIfAvailable(
        activity: Activity,
        onShowAdCompleteListener: OnShowAdCompleteListener
    ) {
        // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
        // class.
        appOpenAdManager?.showAdIfAvailable(activity, onShowAdCompleteListener)
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete
     * (i.e. dismissed or fails to show).
     */
    interface OnShowAdCompleteListener {
        fun onShowAdComplete()

        fun adAdDismissedBackPresed()
    }


    companion object {
        private const val ACTIVITY_MOVES_TO_FOREGROUND_HANDLE = true

        const val NB_VIDEOS_BEFORE_AD: Int = 3
        const val SECONDS_TO_COUNT_VIDEOS: Int = 5
        private const val ONESIGNAL_APP_ID = "8fcf9ed0-0064-49ee-9d27-aca9bb3bd972"

        private lateinit var var0: DexApplication

        @get:Synchronized
        val instance: DexApplication
            get() {
                var aaaaa: DexApplication
                synchronized(DexApplication::class.java) {
                    aaaaa = var0
                }
                return aaaaa
            }
    }
}
