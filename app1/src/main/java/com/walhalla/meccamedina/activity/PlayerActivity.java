//package com.walhalla.meccamedina.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//
//import com.walhalla.meccamedina.DexApplication;
//import com.walhalla.meccamedina.R;
//import com.walhalla.meccamedina.config.Const;
//import com.walhalla.ui.DLog;
//
//import com.walhalla.meccamedina.NetworkStateReceiver;
//import com.walhalla.Utils;
//
//import java.util.Arrays;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class PlayerActivity extends YouTubeBaseActivity
//        implements View.OnClickListener, YouTubePlayer.OnInitializedListener,
//        YouTubePlayer.PlaybackEventListener {
//
//    private MyPlayerStateChangeListener playerStateChangeListener;
//
//    private static final String ARG_IDS = "ytIds";
//    private static final String ARG_POSITION = "position";
//    private static final String ARG_DEVELOPER_KEY = "dkey";
//
//    private ImageButton homeButton;
//    protected InterstitialAd interstitialAd;
//    private LinearLayout layoutAd;
//    private ImageButton nextButton;
//    protected YouTubePlayer player = null;
//    public int position;
//    private ImageButton previousButton;
//    private Timer videoTimer;
//    private TimerTask videoTimerTask = null;
//    public String[] videos;
//
//    private final YouTubePlayer.PlaylistEventListener myPlaylistEventListener = new MyPlaylistEventListener();
//
//
//    public static Intent getInstance(Context context, String[] video_ids, int position) {
//        Intent intent = new Intent(context, PlayerActivity.class);
//        Bundle args = new Bundle();
//        args.putStringArray(ARG_IDS, video_ids);
//        args.putInt(ARG_POSITION, position);
//        intent.putExtras(args);
//        return intent;
//    }
//
////    public static Intent getIntent(final Context context) {
////        return new Intent(context, PlayerActivity.class);
////    }
//
//
//    protected void onCreate(Bundle savedInstanceState) {
////        Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler.getInstance(getApplicationContext()));
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_player);
//        playerStateChangeListener = new MyPlayerStateChangeListener();
//
//        getWindow().addFlags(128);
//        this.videoTimer = new Timer();
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            this.videos = bundle.getStringArray(ARG_IDS);
//            this.position = bundle.getInt(ARG_POSITION);
//        }
//        displayView(NetworkStateReceiver.isConnected(this));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        DexApplication.getInstance().setConnectivityListener(this);
//    }
//
//
//    public void displayView(boolean isConnected) {
//        if (isConnected) {
//            setContentView(R.layout.activity_player);
//            this.previousButton = findViewById(R.id.previous_button);
//            this.nextButton = findViewById(R.id.next_button);
//            this.homeButton = findViewById(R.id.home_button);
//            this.previousButton.setOnClickListener(this);
//
//
////            if (PlayerActivity.this.player != null && PlayerActivity.this.player.hasPrevious()) {
////                PlayerActivity.this.player.previous();
////            }
//
//            this.nextButton.setOnClickListener(this);
//            this.homeButton.setOnClickListener(this);
//
//            ((YouTubePlayerView) findViewById(R.id.youtube_view))
//                    .initialize(Const.GOOGLE_API_KEY, this);
//
//            initAdMob();
//            return;
//        }
//        setContentView(R.layout.activity_missing_connection);
//    }
//
//
//    public void requestNewIntersticial() {
//        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
//            this.interstitialAd.loadAd(Utils.getAdRequest());
//        }
//    }
////    @Override
////    public void replaceFragment(Fragment fragment) {
////        throw new RuntimeException("");
////    }
//
//
//    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return (YouTubePlayerView) findViewById(R.id.youtube_view);
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        this.player = player;
//        this.player.setPlayerStateChangeListener(playerStateChangeListener);
//        this.player.setPlaybackEventListener(this);
//        this.player.setPlaylistEventListener(myPlaylistEventListener);
//        if (!wasRestored) {
//            loadPlaylist();
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                        YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, Const.RECOVERY_DIALOG_REQUEST).show();
//        } else {
//            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Const.RECOVERY_DIALOG_REQUEST) {
//            // Retry initialization if user performed MyWeakReference recovery action
//            getYouTubePlayerProvider().initialize(Const.GOOGLE_API_KEY, this);
//        }
//    }
//
//    public void loadPlaylist() {
//        if (this.videos == null || this.videos.length == 0) {
//            return;
//        }
//        this.player.loadVideos(Arrays.asList(this.videos), this.position, 0);
//    }
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        if (id == R.id.next_button) {
//            if (PlayerActivity.this.player != null && PlayerActivity.this.player.hasNext()) {
//                PlayerActivity.this.player.next();
//            }
//        } else if (id == R.id.previous_button) {
//            if (PlayerActivity.this.player != null && PlayerActivity.this.player.hasPrevious()) {
//                PlayerActivity.this.player.previous();
//            }
//        } else if (id == R.id.home_button) {
//            PlayerActivity.this.finish();
//        }
//    }
//
//    @Override
//    public void onPlaying() {
//        DLog.d("Test Playing, position" + this.position);
//        if (this.videoTimerTask != null) {
//            this.videoTimerTask.cancel();
//        }
//        DexApplication app = (DexApplication) getApplication();
//        if (app.hasToDisplayIntersticiel()) {
//            app.resetIntersticialCounter();
//            showInterstitial();
//            return;
//        }
//        initializeVideoTimerTask();
//        this.videoTimer.schedule(this.videoTimerTask, (long) app.getSecondsBeforeIntersticial());
//    }
//
//    public void initializeVideoTimerTask() {
//        this.videoTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                ((DexApplication) PlayerActivity.this.getApplication()).incIntersticialCounter();
//            }
//        };
//    }
//
//    private void showInterstitial() {
//        if (this.interstitialAd != null && this.interstitialAd.isLoaded()) {
//            this.interstitialAd.show();
//        }
//    }
//
//
//    @Override
//    public void onPaused() {
//
//    }
//
//    @Override
//    public void onStopped() {
//
//    }
//
//    @Override
//    public void onBuffering(boolean b) {
//
//    }
//
//    @Override
//    public void onSeekTo(int i) {
//
//    }
//
//
//    private void updateButtons() {
//        this.nextButton.setEnabled(this.player.hasNext());
//        this.previousButton.setEnabled(this.player.hasPrevious());
//    }
//
//
//    private final class MyPlaylistEventListener implements YouTubePlayer.PlaylistEventListener {
//        @Override
//        public void onNext() {
//            log("NEXT VIDEO");
//            PlayerActivity.this.position++;
//        }
//
//        @Override
//        public void onPrevious() {
//            log("PREVIOUS VIDEO");
//            PlayerActivity.this.position--;
//        }
//
//        @Override
//        public void onPlaylistEnded() {
//            log("PLAYLIST ENDED");
//        }
//    }
//
////    @Override
////    public void onNetworkConnectionChanged(boolean z) {
////        displayView(z);
////    }
//
//    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {
//        String playerState = "UNINITIALIZED";
//
//        @Override
//        public void onLoading() {
//            playerState = "LOADING";
//            updateText();
//            log(playerState);
//        }
//
//        @Override
//        public void onLoaded(String videoId) {
//            playerState = String.format("LOADED %s", videoId);
//            updateText();
//            log(playerState);
//        }
//
//        @Override
//        public void onAdStarted() {
//            playerState = "AD_STARTED";
//            updateText();
//            log(playerState);
//        }
//
//        @Override
//        public void onVideoStarted() {
//            playerState = "VIDEO_STARTED";
//            updateText();
//            log(playerState);
//            updateButtons();
//        }
//
//        @Override
//        public void onVideoEnded() {
//            playerState = "VIDEO_ENDED";
//            updateText();
//            log(playerState);
//            updateButtons();
//        }
//
//        @Override
//        public void onError(YouTubePlayer.ErrorReason reason) {
//            updateButtons();
//            playerState = "ERROR (" + reason + ")";
//            if (reason == YouTubePlayer.ErrorReason.UNEXPECTED_SERVICE_DISCONNECTION) {
//                // When this error occurs the player is released and can no longer be used.
//                player = null;
//                //setControlsEnabled(false);
//            }
//            updateText();
//            log(playerState);
//        }
//
//    }
//
//    private void updateText() {
//    }
//
//    private void log(String playerState) {
//        DLog.d("log: " + playerState);
//    }
//
//
//    protected void initAdMob() {
//
////        if (this.interstitialAd == null) {
////            this.interstitialAd = new InterstitialAd(this);
////            this.interstitialAd.setAdUnitId(getResources().getString(R.string.intersticial_ad_unit_id));
////            this.interstitialAd.setAdListener(new AdListener() {
////
////                @Override
////                public void onAdOpened() {
////                    if (PlayerActivity.this.player != null) {
////                        PlayerActivity.this.player.release();
////                    }
////                }
////                @Override
////                public void onAdClosed() {
////                    ((YouTubePlayerView) PlayerActivity.this.findViewById(R.id.youtube_view))
////                            .initialize(developerKey(), this);
////                    PlayerActivity.this.requestNewIntersticial();
////                }
////            });
////            requestNewIntersticial();
////        }
//    }
//}
