package com.demo.scrapper.receiver;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.walhalla.data.model.DownloadEntity;
import com.walhalla.data.repository.LocalDatabaseRepo;
import com.walhalla.intentresolver.TiktokIntent;
import com.walhalla.ui.DLog;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BroadcastPresenter{

    private final Handler mHandler;
    private ExecutorService executorService;

    public BroadcastPresenter() {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void shareFile(Context context, String video_id) {
        executorService = Executors.newSingleThreadExecutor();executorService.execute(() -> {
            LocalDatabaseRepo m = LocalDatabaseRepo.getStoreInfoDatabase(context);
            boolean k = m.isDownloadExists(video_id);
            if (k) {
                mHandler.post(() -> {
                    if (context instanceof Activity && !((Activity) context).isFinishing()) {
                        mHandler.post(() -> {
                            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                File file = new File("/storage/emulated/0/Movies", video_id + ".mp4");
                boolean exist = file.exists();
                DLog.d("@dwn@" + file);
                new TiktokIntent().shareMp4Selector(context, file);
                m.insertPosted(new DownloadEntity(video_id,video_id+".mp4"));
            }
        });
    }
}
