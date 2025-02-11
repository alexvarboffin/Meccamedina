package com.walhalla.ytlib.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;

import com.walhalla.ytlib.R;
import com.walhalla.ytlib.domen.YT_Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {


    private static String formatId(String o) {
        return o.replace("yt:video:", "");
    }

    public static String[] getIdList(List<YT_Entry> arr) {

        int length = arr.size();
        String ids[] = new String[length];

        for (int i = 0; i < length; i++) {
            ids[i] = formatId(arr.get(i).getId());
        }

        return ids;
    }


    public static final String API_YOUTUBE = "https://www.googleapis.com/youtube/v3/";
    public static final Integer ARG_TIMEOUT_MS = 4000;
    public static final String ARRAY_ITEMS = "items";

    public static final String FUNCTION_PLAYLIST_ITEMS_YOUTUBE = "playlistItems?";
    public static final String FUNCTION_SEARCH_YOUTUBE = "search?";
    public static final String FUNCTION_VIDEO_YOUTUBE = "videos?";


    public static final String OBJECT_ITEMS_CONTENT_DETAIL = "contentDetails";
    public static final String OBJECT_ITEMS_ID = "id";

    public static final String PARAM_CHANNEL_ID_YOUTUBE = "channelId=";
    public static final String PARAM_FIELD_PLAYLIST_YOUTUBE = "fields=nextPageToken,pageInfo(totalResults),items(snippet(title,thumbnails,publishedAt,resourceId(videoId)))";
    public static final String PARAM_FIELD_SEARCH_YOUTUBE = "fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))";
    public static final String PARAM_FIELD_VIDEO_YOUTUBE = "fields=pageInfo(totalResults),items(contentDetails(duration))&";
    public static final String PARAM_KEY_YOUTUBE = "key=";
    public static final String PARAM_MAX_RESULT_YOUTUBE = "maxResults=";
    public static final String PARAM_ORDER_YOUTUBE = "order=date";
    public static final String PARAM_PAGE_TOKEN_YOUTUBE = "pageToken=";
    public static final String PARAM_PART_YOUTUBE = "part=";
    public static final String PARAM_PLAYLIST_ID_YOUTUBE = "playlistId=";
    public static final int PARAM_RESULT_PER_PAGE = 8;
    public static final String PARAM_TYPE_YOUTUBE = "type=video";
    public static final String PARAM_VIDEO_ID_YOUTUBE = "id=";
    public static final String TAG_CHANNEL_ID = "channel_id";
    public static final String TAG_VIDEO_TYPE = "video_type";

    public static void showSnackBar(Activity activity, String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(activity.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Log.i(TAG, "showSnackBar: " + message);
            }
        });
    }

    public static String formatPublishedDate(Context context, String publishedDate) {
        Date result = new Date();
        try {
            result = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA).parse(publishedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimeAgo(result, context);
    }

    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getTimeAgo(Date date, Context ctx) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        if (time > currentDate().getTime() || time <= 0) {
            return null;
        }
        String timeAgo;
        int dim = getTimeDistanceInMinutes(time);
        if (dim == 0) {
            timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim == 1 || dim == 21 || dim == 31 || dim == 41) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minuta);
        } else if (dim == 2 || dim == 3 || dim == 4 || dim == 22 || dim == 23 || dim == 24 || dim == 32 || dim == 33 || dim == 34 || dim == 42 || dim == 43 || dim == 44) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if ((dim >= 5 && dim <= 20) || ((dim >= 25 && dim <= 30) || (dim >= 35 && dim <= 40))) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_unit_hour);
        } else if (dim >= 90 && dim <= 270) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + Math.round((float) (dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hour);
        } else if (dim >= 271 && dim <= 1439) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + Math.round((float) (dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hours);
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_daya);
        } else if (dim >= 2520 && dim <= 6480) {
            timeAgo = Math.round((float) (dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_day);
        } else if ((dim >= 6481 && dim <= 29000) || (dim >= 34701 && dim <= 43200)) {
            timeAgo = Math.round((float) (dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_days);
        } else if (dim >= 29001 && dim <= 30500) {
            timeAgo = Math.round((float) (dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_daya);
        } else if (dim >= 30501 && dim <= 34700) {
            timeAgo = Math.round((float) (dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_day);
        } else if (dim >= 43201 && dim <= 86399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_unit_month);
        } else if (dim >= 86400 && dim <= 216000) {
            timeAgo = Math.round((float) (dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_month);
        } else if (dim >= 216001 && dim <= 492480) {
            timeAgo = Math.round((float) (dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_months);
        } else if (dim >= 492481 && dim <= 518400) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 518401 && dim <= 914399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim < 914400 || dim > 1051199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + Math.round((float) (dim / 525600)) + " " + ctx.getResources().getString(R.string.date_util_unit_years);
        } else {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " + ctx.getResources().getString(R.string.date_util_unit_years);
        }
        return timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix);
    }

    private static int getTimeDistanceInMinutes(long time) {
        return Math.round((float) ((Math.abs(currentDate().getTime() - time) / 1000) / 60));
    }

    public static String getTimeFromString(String duration) {
        String time = "";
        boolean hourexists = false;
        boolean minutesexists = false;
        boolean secondsexists = false;
        if (duration.contains("H")) {
            hourexists = true;
        }
        if (duration.contains("M")) {
            minutesexists = true;
        }
        if (duration.contains("S")) {
            secondsexists = true;
        }
        if (hourexists) {
            String hour = duration.substring(duration.indexOf("T") + 1, duration.indexOf("H"));
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            time = time + hour + ":";
        }
        if (minutesexists) {
            String minutes;
            if (hourexists) {
                minutes = duration.substring(duration.indexOf("H") + 1, duration.indexOf("M"));
            } else {
                minutes = duration.substring(duration.indexOf("T") + 1, duration.indexOf("M"));
            }
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }
            time = time + minutes + ":";
        } else {
            time = time + "00:";
        }
        if (!secondsexists) {
            return time + "00";
        }
        String seconds;
        if (hourexists) {
            if (minutesexists) {
                seconds = duration.substring(duration.indexOf("M") + 1, duration.indexOf("S"));
            } else {
                seconds = duration.substring(duration.indexOf("H") + 1, duration.indexOf("S"));
            }
        } else if (minutesexists) {
            seconds = duration.substring(duration.indexOf("M") + 1, duration.indexOf("S"));
        } else {
            seconds = duration.substring(duration.indexOf("T") + 1, duration.indexOf("S"));
        }
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        return time + seconds;
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);


//        GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE;
//        boolean resolvableError = apiAvailability.isUserResolvableError(connectionStatusCode);
//        DLog.d("@@@ resolvableError [" + resolvableError + "]\t" + GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    public static boolean isDeviceOnline(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }


}
