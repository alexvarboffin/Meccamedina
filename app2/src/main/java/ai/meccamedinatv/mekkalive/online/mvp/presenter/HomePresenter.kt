package ai.meccamedinatv.mekkalive.online.mvp.presenter

import ai.meccamedinatv.mekkalive.online.DexApplication
import ai.meccamedinatv.mekkalive.online.R
import ai.meccamedinatv.mekkalive.online.config.Const
import ai.meccamedinatv.mekkalive.online.repository.YoutubeRepository
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.GenericJson
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.SearchListResponse
import com.walhalla.ui.DLog
import com.walhalla.ytlib.domen.ListEntryUI
import com.walhalla.ytlib.home.HomeView
import com.walhalla.ytlib.repository.Const.REQUEST_AUTHORIZATION
import com.walhalla.ytlib.repository.FirebaseRepository
import com.walhalla.ytlib.repository.Repository
import com.walhalla.ytlib.utils.Utils
import java.net.UnknownHostException
import java.util.Arrays
import java.util.LinkedList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import moxy.InjectViewState
import moxy.MvpPresenter


//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.extensions.android.http.AndroidHttp;
@InjectViewState
class HomePresenter: MvpPresenter<HomeView>(), Repository.Callback<GenericJson> {

    private val mExecutorService: ExecutorService

    private val context: Context
    private val youtubeRepository: YoutubeRepository
    private val firebaseRepository: FirebaseRepository

    private var mIsAppFirstLaunched = true
    private var mIsFirstVideo = true


    private var mNextPageToken = ""
    private val mIsStillLoading = true
    private var mCredential: GoogleAccountCredential? = null

    //All video
    private var uiList: LinkedList<ListEntryUI> = LinkedList<ListEntryUI>()
    private var position = 0


    //Payload
    //    private static List<ListEntry> _menu = new ArrayList<>();
    //    static {
    //        ListEntry data = new ListEntry(
    //                "EgDHjVTwig0", "قناة القران الكريم | مكة المكرمة بث مباشر| Makkah Live HD | Masjid Al Haram | La Makkah en direct 24/7 Live Stream of Masjid Al Haram from Makkah . بث مباشر...", null,
    //                "https://i.ytimg.com/vi/EgDHjVTwig0/default_live.jpg", null, 3
    //        );
    //        ListEntry data1 = new ListEntry(
    //                "wrAlBdlT-Og", "Support the stream: https://streamlabs.com/alquranhd قناة السنة النبوية | المدينة المنورة بث مباشر| Madinah Live HD | Masjid Nabawi | La Madina en direct...", null,
    //                "https://i.ytimg.com/vi/VO6Zw54YCeY/hqdefault_live.jpg", null, 3
    //        );
    //        _menu.add(data);
    //        _menu.add(data1);
    //    }
    //FF:18:23:E8:B0:05:F2:5D:45:4B:B0:80:D3:F1:9E:99:51:C7:1E:48
    init {
        DLog.d("HomePresenter")
        context = DexApplication.getInstance()
        val TRANSPORT: HttpTransport = NetHttpTransport()
        val jsonFactory: JsonFactory = GsonFactory()

        val SCOPES = arrayOf(
            YouTubeScopes.YOUTUBE_READONLY,  //YouTubeScopes.YOUTUBE
        )

        val youTube: YouTube
        if (Const.WITH_USER_OAUTH) {
            mCredential = GoogleAccountCredential
                .usingOAuth2(context, Arrays.asList(*SCOPES))
                .setBackOff(ExponentialBackOff())

            //With user Oauth Credential
            youTube = YouTube.Builder(
                TRANSPORT, jsonFactory, mCredential
            )
                .setApplicationName(context.getString(R.string.app_name))
                .build()
        } else {
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
//                    .usingAudience(context, "ooo");
//            // set account, credential tries to fetch Google account from Android AccountManager
//            mCredential.setSelectedAccountName(null);
            //==========================================================================================
//        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
//                .enableAutoManage(null /* FragmentActivity */,
//                        null /* OnConnectionFailedListener */)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .build();
            youTube = YouTube.Builder(
                TRANSPORT, jsonFactory
            ) { request: HttpRequest? -> }
                .setApplicationName(context.getString(R.string.app_name))
                .build()
        }

        //HttpTransport TRANSPORT = AndroidHttp.newCompatibleTransport();
        //JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        //JsonFactory factory = new JsonFactory();
        //JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        youtubeRepository = YoutubeRepository(context, youTube)
        firebaseRepository = FirebaseRepository()
        mExecutorService = Executors.newSingleThreadExecutor()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun attachView(view: HomeView?) {
        super.attachView(view)
        getResultsFromApi(DexApplication.getInstance())
    }

    override fun onMessageRetrieved(raw: GenericJson) {
        if (raw is PlaylistItemListResponse) {
            val response = raw
            viewState!!.hideLoading()
            val arr = response.items

            if (arr != null && arr.size > 0) {
                viewState!!.haveResultView()

                val videoIds0: MutableList<String> = ArrayList(arr.size)
                uiList = LinkedList()

                for (item in arr) {
                    val snippet = item.snippet
                    val resourceId = snippet.resourceId
                    val videoId = resourceId.videoId

                    if (videoId != "G99rj3aC-ko") {
                        val entry = ListEntryUI()

                        val thumbnails = snippet.thumbnails
                        if (thumbnails != null) {
                            val thumbnail = thumbnails.medium
                            entry.setUrlThumbnails(thumbnail.url)
                        }
                        entry.setVideoId(videoId)
                        entry.title = (snippet.title)
                        entry.setPublishedAt(
                            Utils.formatPublishedDate(
                                context,
                                snippet.publishedAt.toString()
                            )
                        )
                        uiList.add(entry)
                        videoIds0.add(videoId)
                    }
                }


                if (mIsFirstVideo /* && i == 0*/) {
                    mIsFirstVideo = false
                    viewState!!.onVideoItemSelected(videoIds0[0])
                }


                //-------------------
                if (!SKIP_DURATION) {
                    youtubeRepository.getDuration(videoIds0)
                }
                if (arr.size == 8) {
                    mNextPageToken = response.nextPageToken
                } else {
                    mNextPageToken = ""
                    viewState!!.disableLoadMore("44444")
                }
                mIsAppFirstLaunched = false
            } else {
                viewState!!.noResultView()
                viewState!!.disableLoadMore("555555")
            }
        } else if (raw is SearchListResponse) {
            viewState!!.hideLoading()
            val items = raw.items
            if (items != null && items.size > 0) {
                handleChannelList(raw)
            } else {
                val empty = true
                if (mIsAppFirstLaunched && empty) {
                    viewState!!.noResultView()
                }
                viewState!!.disableLoadMore("333333")
            }
        }
    }


    override fun onRetrievalFailed(s: String, mLastError: Exception?) {
        //DLog.d("{" + s + "}");
        //if (getActivity() != null && isAdded()) {


        viewState!!.hideLoading()
        if (mLastError is UnknownHostException) {
            viewState!!.showError(R.string.error_connection)
        } else if (mLastError is GooglePlayServicesAvailabilityIOException) {
            viewState!!.showGooglePlayServicesAvailabilityErrorDialog(
                mLastError
                    .connectionStatusCode
            )
        } else if (mLastError is UserRecoverableAuthIOException) {
            viewState!!.startActivityForResult(
                mLastError.intent, REQUEST_AUTHORIZATION
            )
        } else if (mLastError is GoogleJsonResponseException) {
            val errorMessage = mLastError.message
            val code = mLastError.statusCode

            //403 Forbidden
            DLog.d("$errorMessage | $code")
            if (403 == code) {
                loadFromFirebase(false)
                saveLastFailedRequestTime(context, System.currentTimeMillis())
            } else {
                //Ошибка в настройках проекта
                viewState!!.errorResult("The following error occurred:\n$errorMessage")
                viewState!!.showError(mLastError.javaClass.simpleName, errorMessage)
            }
        } else {
            val errorMessage = mLastError?.message
            errorMessage?.let {
                viewState!!.errorResult("The following error occurred:\n$it")
                viewState!!.showError(mLastError.javaClass.simpleName, it)
            }
        }


//                                try {
//                                    int msg;
//                                    if (error instanceof NoConnectionError) {
//                                        msg = R.string.no_internet_connection;
//                                    } else {
//                                        msg = R.string.response_error;
//                                    }
//                                    if (mVideoData.size() == 0) {
//                                        getViewState().retryView();
//                                    } else {
//                                        getViewState().setCustomLoadMoreView(null);
//                                    }
//                                    getViewState().showError(msg);
//
//                                } catch (Exception mLastError) {
//                                    DLog.d("failed catch volley " + mLastError.toString());
//                                }
//                                //}


        //SearchListResponse
        //getViewState().errorResponse(mLastError);


//                        //if (getActivity() != null && isAdded()) {
//                        DLog.d("on Error Response: " + error.getMessage());
//
//                        getViewState().hideLoading();
//
//                        try {
//                            int msg;
//                            if (error instanceof NoConnectionError) {
//                                msg = R.string.no_internet_connection;
//                            } else {
//                                msg = R.string.response_error;
//                            }
//                            if (mVideoData.size() == 0) {
//                                getViewState().retryView();
//                            } else {
//                                getViewState().setCustomLoadMoreView(null);
//                            }
//                            getViewState().showError(msg);
//
//                        } catch (Exception mLastError) {
//                            DLog.d("failed catch volley " + mLastError.toString());
//                        }
//                        //}
    }


    fun onMenuItemSelected(position: Int) {
        if (uiList != null && uiList!!.size > position) {
            val mm = uiList!![position]
            viewState!!.showVideoFragment(mm.type, mm.getVideoId())
        }
    }


    /*
     * =============================================================

     * =============================================================
     */
    fun setSelectedAccountName(accountName: String?) {
        mCredential!!.setSelectedAccountName(accountName)
    }

    fun getResultsFromApi(context: Context) {
        if (!Utils.isGooglePlayServicesAvailable(context)) {
            acquireGooglePlayServices(context)
        } else if (Const.WITH_USER_OAUTH && mCredential!!.selectedAccountName == null) {
            DLog.d("@@[chooseAccount]@@")
            viewState!!.chooseAccount(mCredential)
        } else if (!Utils.isDeviceOnline(context)) {
            viewState!!.errorResult("No network connection available.")
        } else {
            //new MakeRequestTask(mCredential).execute();
            loadPlaylistData(this.context)
        }
    }

    private fun acquireGooglePlayServices(context: Context) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            viewState!!.showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
        }
    }


    fun onVideoItemSelected(position: Int) {
        viewState!!.onVideoItemSelected(uiList[position].getVideoId())
    }

    fun getVideoDataClick() {
        if (!uiList.isEmpty()) {
            getVideoData(uiList, position)
        } else {
            getResultsFromApi(context)
        }
    }

    private fun loadPlaylistData(context: Context) {
//        mRepository.getEntryArrayAsync(
//                "UCmM6mEXyj-T-7TXFu1DwCFw",
//
//                new YT_Repository.AdvertCallback<YT_Entry>() {
//
//                    @Override
//                    public void renderVideoList(List<PlaylistItem> result) {
//
//                        arr = result;
//
//                        if (result != null && !result.isEmpty()) {
//                            List<Object> objects = new ArrayList<>();
//                            objects.addAll(result);
//                            getViewState().showData(objects);
//                            return;
//                        }
//
//                        getViewState().showError(R.string.app_name);
//                    }
//                }
//        );

        if (uiList.isEmpty()) {
//            int[] mVideoTypes = context.getResources().getIntArray(R.array.video_type);
//            String[] titles = context.getResources().getStringArray(R.array.channel_names);
//            String[] mChannelIds = context.getResources().getStringArray(R.array.channel_ids);
////     <item>Makkah Live HD</item>
////        <item>Madinah Live HD</item>
//            listEntries = new ArrayList<ListEntry>(mVideoTypes.length+1);
//            listEntries.add(new ListEntry(CHANNEL_ID, "ch",null,
//                    null, null,1));
//            for (int i = 0; i < mVideoTypes.length; i++) {
//                listEntries.add(new ListEntry(
//                        mChannelIds[i], titles[i], null,
//                        null, null, mVideoTypes[i]));
//            }


//            try {
//                listEntries = FileUtil.loadResource(context);
//                if (listEntries != null && noneNullState()) {


//                }
//            } catch (FileNotFoundException e) {
//                DLog.handleException(e);
//            }


            //showDemo();


            checkAndSendRequest(context)

            //
//AlQuranHD        }https://www.youtube.com/channel/UCraPI8sg-eiNzUrurxhKeEQ
        }
    }


    private fun checkAndSendRequest(context: Context) {
        val currentTime = System.currentTimeMillis()
        val lastFailedRequestTime = getLastFailedRequestTime(context)
        // Check if 24 hours have passed since the last successful request
        if (currentTime - lastFailedRequestTime >= TWENTY_FOUR_HOURS_IN_MILLIS) {
            loadFromYouTube()
        } else {
            loadFromFirebase(true)
        }
    }

    private fun loadFromFirebase(tryOtherRepo: Boolean) {
        firebaseRepository.loadPlaylist(object : Repository.Callback<List<ListEntryUI>> {
            override fun onMessageRetrieved(data: List<ListEntryUI>) {
                if (mIsAppFirstLaunched) {
                    DLog.d("@@@@" + data.size + "@@" + Thread.currentThread())
                    this@HomePresenter.uiList = LinkedList(data)
                    viewState!!.showDrawerMenuList(uiList)
                    onMenuItemSelected(0)
                }
                mIsAppFirstLaunched = false
                //                    getViewState().hideLoading();
//                    if (listEntries != null && listEntries.size() > 0) {
//                        getViewState().haveResultView();
//                        getViewState().renderVideoList(listEntries, 0);
//
//                    } else {
//                        if (mIsAppFirstLaunched) {
//                            getViewState().noResultView();
//                        }
//                        getViewState().disableLoadMore("333333");
//                    }
            }


            override fun onRetrievalFailed(s: String, e: Exception?) {
                if (tryOtherRepo) {
                    loadFromYouTube()
                }
            }
        })
    }

    private fun handleChannelList(response: SearchListResponse) {
        try {
            val items = response.items
            viewState!!.haveResultView()

            val videoIds: MutableList<String> = ArrayList(items.size)
            uiList = LinkedList()
            for (item in items) {
//                handleItem_START
                val snippet = item.snippet
                val snippetTitle = snippet.title
                val resourceId = item.id
                val videoId = resourceId.videoId
                if (videoId != "G99rj3aC-ko") {
                    if (!snippetTitle.contains("#youtubeshorts")
                        && !snippetTitle.contains("#shorts")
                    ) {
                        val entryUI = ListEntryUI()
                        val thumbnails = snippet.thumbnails

                        DLog.d("@@$resourceId")
                        entryUI.setVideoId(videoId)
                        entryUI.title = snippetTitle
                        entryUI.setPublishedAt(
                            Utils.formatPublishedDate(
                                context,
                                snippet.publishedAt.toString()
                            )
                        )
                        entryUI.type = Const.TYPE_VIDEO
                        entryUI.setDuration("live")
                        if (thumbnails != null) {
                            val thumbnail = thumbnails.medium
                            entryUI.setUrlThumbnails(thumbnail.url)
                        }
                        uiList.add(entryUI)
                        videoIds.add(resourceId.videoId)
                        //"snippet,contentDetails,brandingSettings"
                    }
                }


                //if (BuildConfig.DEBUG) {
                //DLog.d("{video}: " + item);
                //}
//                handleItem_END
            }

            if (mIsFirstVideo /* && i == 0*/) {
                mIsFirstVideo = false
                viewState!!.onVideoItemSelected(videoIds[0])
            }

            //-------------------
            if (!SKIP_DURATION) {
                youtubeRepository.getDuration(videoIds)
            }

            //===================
            if (items.isNotEmpty()) {
                mNextPageToken = response.nextPageToken
            } else {
                mNextPageToken = ""
                viewState!!.disableLoadMore("22222")
            }

            //Cycle
//                if (mNextPageToken != null && !mNextPageToken.isEmpty()) {
//                    String channelId = response.getItems().get(0).getSnippet().getChannelId();
//                    mRepository.searchRequestAsync(channelId, mNextPageToken, this);
//                }

            //Save
            if (mIsAppFirstLaunched && this@HomePresenter.uiList.isNotEmpty() && noneNullState()) {
                //FileUtil.createAndSaveFile(mContext, new Gson().toJson(HomePresenter.this.data0));

                firebaseRepository.saveList(uiList)

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    viewState!!.showDrawerMenuList(uiList)
                    onMenuItemSelected(0)
                }
            }

            mIsAppFirstLaunched = false
        } catch (e1: Exception) {
            this@HomePresenter.onRetrievalFailed("123456", e1)
        }
    }

    private fun showDemo() {
        uiList = LinkedList()
        uiList.add(ListEntryUI())
        uiList.add(ListEntryUI())
        uiList.add(ListEntryUI())
        uiList.add(ListEntryUI())
        uiList.add(ListEntryUI())
        uiList.add(ListEntryUI())

        viewState!!.showDrawerMenuList(uiList)
        onMenuItemSelected(0)
        //getViewState().renderVideoList(listEntries, 0);
    }

    private fun loadFromYouTube() {
        val CHANNEL_ID = context.resources.getStringArray(R.array.channel_ids)
        //mRepository.getUnderratedLiveBroadcasts(CHANNEL_ID, "", this);

        mExecutorService.submit {
            youtubeRepository.searchRequestAsync(
                CHANNEL_ID,
                "",
                Const.WITH_USER_OAUTH,
                this@HomePresenter
            )
        }
    }


    fun fragmentConnected() {
        viewState!!.renderVideoList(uiList, 0)
    }


    /**
     * RENDER CURRENT VIDEO, GET SOME DATA BY ID
     *
     * @param entry
     */
    private fun getVideoData(entry: List<ListEntryUI>, position: Int) {
        this.position = position

        if (noneNullState()) {
            viewState!!.showLoading()
        }

        //if (getActivity() != null && isAdded()) {
        //}
//        try {
//            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            //DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            System.exit(1);
//        }
//
//        HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        HttpRequestInitializer credential = new HttpRequestInitializer() {
//            @Override
//            public void mInit(HttpRequest request) throws IOException {
//
//            }
//        };
        val type = entry[position].type
        when (type) {
            Const.TYPE_VIDEO -> {
                //new TestAsyncRequest(CHANNEL_ID, mNextPageToken, youTube).execute();


                //data1.setPublishedAt(Utils.formatPublishedDate(mContext, snippet.getPublishedAt().toString()));
                if (noneNullState()) {
                    viewState!!.onVideoItemSelected(entry[position].getVideoId())
                }
                //        mVideoData.add(data);
                //        mVideoData.add(data1);
                //ddddddddddddd(mVideoData);
                //getDuration(Collections.singletonList(mChannelId));
                viewState!!.disableLoadMore("66666666")
            }

            Const.TYPE_PLAY_LIST -> {
                val playListId = entry[position].getVideoId()
                //youtubeRepository.playListResponseAsync(playListId, mNextPageToken, this)
                playListResponseAsync(playListId, mNextPageToken, this)
            }

            Const.TYPE_PLAY_SEARCH -> {
                //@@@  new TestAsyncRequest(ENTRY.id, mNextPageToken, youTube).execute();
                val channelId = entry[position].getVideoId()
                youtubeRepository.searchRequestAsync(
                    arrayOf(channelId), mNextPageToken, Const.WITH_USER_OAUTH,
                    this
                )
            }

            else -> {
                //DLog.d(((("@@@" + R.integer._PLAY_LIST).toString() + " " + R.integer._SEARCH).toString() + " @ False video type: " + entry[position].toString()))
            }
        }
        DLog.d("@@@-$type")
    }
    //PlaylistItemListResponse
    private fun playListResponseAsync(
        playListId: String,
        pageToken: String?,
        callback: Repository.Callback<GenericJson>
    ) {
        mExecutorService.submit {
            youtubeRepository.playListResponse(
                Const.WITH_USER_OAUTH,
                Const.GOOGLE_API_KEY,
                playListId,
                pageToken,
                callback
            )
        }
    }
    private fun noneNullState(): Boolean {
        return viewState != null
    }

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"

        private const val LAST_FAILED_REQUEST_TIME = "lastFailedRequestTime0"
        private const val TWENTY_FOUR_HOURS_IN_MILLIS = (6 * 60 * 60 * 1000).toLong()

        fun saveLastFailedRequestTime(context: Context, time: Long) {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putLong(LAST_FAILED_REQUEST_TIME, time)
            editor.apply()
        }

        fun getLastFailedRequestTime(context: Context): Long {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return preferences.getLong(LAST_FAILED_REQUEST_TIME, 0)
        }

        private const val SKIP_DURATION = true
    }
}
