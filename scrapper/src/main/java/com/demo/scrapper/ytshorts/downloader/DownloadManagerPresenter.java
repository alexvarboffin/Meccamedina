package com.demo.scrapper.ytshorts.downloader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.demo.scrapper.Const;
import com.demo.scrapper.DownloadFile;
import com.demo.scrapper.MyYoutubeRepository;
import com.demo.scrapper.R;
import com.demo.scrapper.UIUtils;
import com.demo.scrapper.YoutubeShortsLoader;
import com.demo.scrapper.YoutubeVideo;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.PageInfo;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.walhalla.data.model.DownloadEntity;
import com.walhalla.data.repository.LocalDatabaseRepo;
import com.walhalla.intentresolver.TiktokIntent;
import com.walhalla.permissionresolver.permission.IOUtils;
import com.walhalla.ui.DLog;
import com.walhalla.ytlib.repository.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManagerPresenter extends TBasePresenter {

    private static final String TAG = "@@@";

    private static final boolean USE_PARTIAL_ACCESS = true;
    private static final String NEXTPAGETOKEN = "";


    private final View mView;

    // Нужные разрешения для старых версий Android
    public static String[] storge_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    // Нужные разрешения для Android 33
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            //Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };
    private ExecutorService executorService;
    private final LocalDatabaseRepo m;
    private final SharedPreferences p;

    public interface View {
        void updateGUI();

        void showPermission33SnackBar();

        void showMessage(String s);
    }

    private Boolean oldValueGranted;

    private final Handler mThread;
    private final GoogleAccountCredential mCredential;
    private final MyYoutubeRepository youtubeRepository;

    ActivityResultLauncher<String[]> launcher29;

    public DownloadManagerPresenter(View view, Handler handler, AppCompatActivity activity,
                                    ActivityResultLauncher<Intent> storageActivityResultLauncher) {
        super(activity, storageActivityResultLauncher);
        this.mThread = handler;
        this.executorService = Executors.newSingleThreadExecutor();
        this.mView = view;
        launcher29 = activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), map -> {
            for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                boolean isGranted = entry.getValue();
                if (isGranted) {
                    Toast.makeText(activity, "GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "NOT GRANTED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final HttpTransport TRANSPORT = new NetHttpTransport();
        final JsonFactory jsonFactory = new GsonFactory();

        String[] SCOPES = new String[]{
                YouTubeScopes.YOUTUBE_READONLY,
                //YouTubeScopes.YOUTUBE
        };

        YouTube youTube;
        if (Const.WITH_USER_OAUTH) {
            mCredential = GoogleAccountCredential
                    .usingOAuth2(activity, Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff());

            //With user Oauth Credential
            youTube = new YouTube.Builder(
                    TRANSPORT, jsonFactory, mCredential)
                    .setApplicationName(activity.getString(R.string.app_name))
                    .build();
        } else {
            mCredential = null;
            // This OAuth 2.0 access scope allows for read-only access to the
            // authenticated user's account, but not other types of account access.
            //YouTubeScopes.YOUTUBE_READONLY
//            List<String> scopes = Lists.newArrayList();
//            scopes.add(
//                    //YouTubeScopes.YOUTUBE //"https://www.googleapis.com/auth/youtube"
//                    YouTubeScopes.YOUTUBE_READONLY
//            );
            //2
//            mCredential = GoogleAccountCredential
//                    .usingAudience(activity, "ooo");
//            // set account, credential tries to fetch Google account from Android AccountManager
//            mCredential.setSelectedAccountName(null);
            //==========================================================================================
//        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(activity)
//                .enableAutoManage(null /* FragmentActivity */,
//                        null /* OnConnectionFailedListener */)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .build();
            youTube = new YouTube.Builder(TRANSPORT, jsonFactory, request ->
            {
            })
                    .setApplicationName(activity.getString(R.string.app_name))
                    .build();
        }

        //HttpTransport TRANSPORT = AndroidHttp.newCompatibleTransport();
        //JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        //JsonFactory factory = new JsonFactory();
        //JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        youtubeRepository = new MyYoutubeRepository(youTube, Const.GOOGLE_API_KEY);
        //firebaseRepository = new FirebaseRepository();

        m = LocalDatabaseRepo.getStoreInfoDatabase(context);
        p = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void test0(Context context) {

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {


            DownloadEntity entity = m.selectNotPosted();
            List<DownloadEntity> all = m.selectAll();

            DLog.d("@@@" + entity + "@@" + all);
            if (isDatabaseEmpty(entity)) {
                youtubeRepository.scrapShorts(context, "motivational quotes", new Repository.Callback<SearchListResponse>() {
                    @Override
                    public void onMessageRetrieved(SearchListResponse response) {
                        DLog.d(String.valueOf(response));
                        List<SearchResult> results = response.getItems();
                        PageInfo info = response.getPageInfo();
                        //response.getPageInfo().getTotalResults();
                        String nextPageToken = response.getNextPageToken();

                        //results.size() == MaxResults

                        //"pageInfo":{"totalResults":1000000}
                        for (SearchResult result : results) {
                            String videoId = result.getId().getVideoId();
                            m.insertNewValue(new DownloadEntity(videoId, videoId + ".mp4"));
                        }

                        p.edit().putString(NEXTPAGETOKEN, nextPageToken).apply();

                        //try load
                        test0(context);
                    }

                    @Override
                    public void onRetrievalFailed(String s, Exception e) {
                        DLog.handleException(e);
                    }
                });
            } else {
                YoutubeVideo m = new YoutubeVideo(entity.id);
                loadFile(m);
            }

        });


    }

    private boolean isDatabaseEmpty(DownloadEntity entity) {
        return entity == null;
    }

    public static YoutubeVideo video0;

    private void loadFile(YoutubeVideo video) {


        video0 = video;

        File file = new File("/storage/emulated/0/Movies", video.video_id + ".mp4");
        boolean exist = file.exists();

        DLog.d("#### TRY LOADING ###" + file.getAbsolutePath() + " " + exist);

        if (exist) {
            shareIfExist(video0, file);
        } else {
            YoutubeShortsLoader.videoLoader(context, video, new Repository.Callback<>() {
                @Override
                public void onMessageRetrieved(YoutubeVideo data) {
                    DownloadFile.newInstance().makeLoad77(context, data, "mp4", new Repository.Callback<String>() {

                        @Override
                        public void onMessageRetrieved(String data) {
                            mThread.post(() -> {
                                UIUtils.showToast0(context, "Downloading Start!");
                            });
                        }

                        @Override
                        public void onRetrievalFailed(String s, Exception e) {
                            mView.showMessage("@@@@@" + s + " " + e.getLocalizedMessage());
                        }
                    });
                }

                @Override
                public void onRetrievalFailed(String s, Exception e) {
                    mView.showMessage("@@@@@" + s + " " + e.getLocalizedMessage());
                }
            });
        }
    }

    private void shareIfExist(YoutubeVideo video, File file) {
        executorService.execute(() -> {
            boolean k = m.isDownloadExists(video.video_id);
            if (k) {
                mThread.post(() -> {
                    if (context instanceof Activity && !((Activity) context).isFinishing()) {
                        mThread.post(() -> {
                            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                DLog.d("@dwn@" + file);
                new TiktokIntent().shareMp4Selector(context, file);


                m.insertPosted(new DownloadEntity(video.video_id, video.video_id + ".mp4"));
            }
        });
    }
//    private void checkAndRequestPermissions(Context context) {
//
////        if (ma != null && !ma.isNeedGrantPermission0()) {
////            updateGUI(getActivity().getContentResolver(), getActivity(), true);
////        }
//
//        oldValueGranted = perm.isGrantedPermissionForGallery(getContext());
//        if (oldValueGranted) {
//            // Permission is granted. Update the GUI
//            updateGUI(getActivity().getContentResolver(), getActivity(), true);
//
//        } else {
//
////            if (ActivityCompat.shouldShowRequestPermissionRationale(context, REQUEST_PERMISSION[0])) {
////                String msg =
////                        String.format(context.getString(com.walhalla.permissionresolver.@@@@@@@@@@@@@),
////                                context.getString(com.walhalla.permissionresolver.R.string.app_name));
////
////                AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
////                localBuilder.setTitle(com.walhalla.permissionresolver.R.string.alert_perm_title);
////                localBuilder.setMessage(msg)
////                        .setNeutralButton("Grant", (dialog, which) -> ActivityCompat.requestPermissions(
////                                context, REQUEST_PERMISSION, REQUEST_PERMISSION_CODE));
////                localBuilder.setNegativeButton(
////                        android.R.string.cancel, (dialog, which) -> {
////                            dialog.dismiss();
////                            //finish();
////                        });
////                localBuilder.show();
////
////            } else {
////                ActivityCompat.requestPermissions(context, REQUEST_PERMISSION, REQUEST_PERMISSION_CODE);
////            }
//            // Permission is not granted. Request it
//            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//    }

    public boolean isNeedGrantPermission() {
        try {
            if (FULL_STORAGE_ACCESS) {
                if (openAllFilesAccessPermission30()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        openManageAllFiles();
                    }
                    return true;
                } else {
                    return false;
                }
            } else if (IOUtils.hasMarsallow23()) {//23-29
                //int result = ContextCompat.checkSelfPermission(activity, REQUEST_PERMISSION[0]);
                if (!hasPermissionsList(context, permissions())) {
                    String[] perms = permissions();
                    if (context instanceof Activity) {
                        if (shouldShowRequestPermissionRationale00((Activity) context, perms)) {
                            showRequestPermissionDialog(context, perms, (dialog, which) -> {
                                launcher29.launch(perms);
                            });
                        } else {
                            launcher29.launch(perms);
                        }
                    } else {
                        launcher29.launch(perms);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "isNeedGrantPermission: " + e.getLocalizedMessage());
        }
        return false;
    }


    // Определяем перечень нужных разрешений для текущей версии Android
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkSelfPermissionOrLaunch33() {
        if (openAllFilesAccessPermission30()) {
            //openManageAllFiles();
            mView.showPermission33SnackBar();
        }
//                else if (USE_PARTIAL_ACCESS) {
//
//                    boolean m13 = false;
//
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
////                        boolean v0 = ContextCompat.checkSelfPermission(context, "android.permission.READ_MEDIA_IMAGES") == PERMISSION_GRANTED;
////                        boolean v1 = ContextCompat.checkSelfPermission(context, READ_MEDIA_VIDEO) == PERMISSION_GRANTED;
////
////                        m13 = v0 || v1;
////
////                        DLog.d("Full access on Android 13+? " + v0+"@@"+v1+"@@"+m13);
////
////                        checkSelfPermissionOrLaunch(context, "android.permission.READ_MEDIA_IMAGES");
////                        //checkSelfPermissionOrLaunch(context, READ_MEDIA_VIDEO);
////
////                    }
//
////
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                        boolean v0 = ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED;
//                        DLog.d("// Partial access on Android 14+"+v0);
//                    }
////                    else if (ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
////                        DLog.d("// Full access up to Android 12");
////                    } else {
////                        DLog.d("// Access denied");
////                    }
//                }
        else {
            mView.updateGUI();
        }
    }
}

