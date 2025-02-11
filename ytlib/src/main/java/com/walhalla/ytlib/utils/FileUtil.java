package com.walhalla.ytlib.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.walhalla.ui.DLog;
import com.walhalla.ytlib.R;
import com.walhalla.ytlib.domen.ListEntryUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.List;

public class FileUtil {

    private static final String var0 = "/raw.json";
    private static final String Q_NAME = "quran.pdf";

    static String PDF_MIME_TYPE = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");

    public static void createAndSaveFile(Context context, String json) {
        try {
            //makedir(REPLICANT_STORAGE_ROOT);
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + var0);
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DLog.d("Created: *" + json);
    }

    public static List<ListEntryUI> loadResource(Context context) throws FileNotFoundException {
        String file = context.getFilesDir().getPath() + var0;
//        new File(file).delete();
//        DLog.d(file);
        Gson gson = new GsonBuilder()
                //.setPrettyPrinting()
                .create();
        Reader reader = new FileReader(file);
        return gson.fromJson(reader, new TypeToken<List<ListEntryUI>>() {
        }.getType());
    }


    public static void copyReadAssets(Activity activity) {


//        try {
//            //makedir(REPLICANT_STORAGE_ROOT);
//            FileWriter file = new FileWriter(activity.getFilesDir().getPath() + "/" + Q_NAME);
//            file.write("123");
//            file.flush();
//            file.close();
//            DLog.d(new File(activity.getFilesDir().getPath() + "/" + Q_NAME).getAbsolutePath());
//            DLog.d(""+new File(activity.getFilesDir().getPath() + "/" + Q_NAME).exists());
//
//        } catch (IOException e) {
//            DLog.handleException(e);
//        }


        File file = new File(activity.getFilesDir(), Q_NAME);
        boolean created = file.exists();
//        if (!created) {
//            try {
//                created = file.createNewFile();
//            } catch (IOException e) {
//                DLog.handleException(e);
//            }
//        }

        if (!created) {
            AssetManager assetManager = activity.getAssets();
            InputStream in;
            OutputStream out;

            try {
                in = assetManager.open(Q_NAME);
                out = new FileOutputStream(file);
                //out = activity.openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                DLog.handleException(e);
            }
        }

        DLog.d("N_E " + created + " " + file.getAbsolutePath());

        try {
            openOldStyle(activity, file);
        } catch (Exception e) {
            DLog.handleException(e);
            //openOldStyle(activity, file);
        }
    }

    private static void err(Activity activity, File file) {
        //Toast.makeText(activity, R.string.pdf_viewer_not_installed, Toast.LENGTH_SHORT).show();

        com.github.pdfviewer.PDFView
                .with(activity)
                .fromfilepath(file.getAbsolutePath())
                //.setSwipeOrientation(VERTICAL) //if false pageswipe is vertical otherwise its horizontal
                .swipeHorizontal(false)
                .start();
    }

    private static void openOldStyle(Activity activity, File file) {

//        PDFConfig config0 = new PDFConfig();
//        config0.setFilepath(file.getAbsolutePath());
//        config0.setSwipeorientation(1);
//        DLog.d("@@" + config0);


        //content://ai.meccamedinatv.mekkalive.online.fileprovider/my_pdf_doc/quran.pdf

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Uri uri;
        // Uri.parse("file://" + activity.getFilesDir() + "/" + Q_NAME);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, PDF_MIME_TYPE); // For now there is only type 1 (PDF).


        List<ResolveInfo> infos = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : infos) {
            String packageName = resolveInfo.activityInfo.packageName;
            String activityName = resolveInfo.activityInfo.name;
            DLog.d("[" + packageName + "]" + activityName);
            activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }


        if (Build.VERSION.SDK_INT == 21) {
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                    activity.startActivity(myIntent);
                } catch (ActivityNotFoundException e0) {
                    err(activity, file);
                }
            }
        } else {
            if (!infos.isEmpty()) {
                for (ResolveInfo info : infos) {
                    String packageName = info.activityInfo.packageName;
                    String activityName = info.activityInfo.name;
                    DLog.d(packageName + "|" + activityName);
                }
                try {
                    Intent chooser = Intent.createChooser(intent, activity.getString(R.string.share_data));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    activity.startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    err(activity, file);
                }
            } else {
                DLog.d("@@@");
                err(activity, file);
            }
        }
    }


    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
