package com.example.incomingphone.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demo.scrapper.ytshorts.downloader.DownloadManagerPresenter;
import com.example.incomingphone.accessibility.uploadhandler.DefaultUploadHandler;
import com.example.incomingphone.accessibility.uploadhandler.TikTokUploadHandler;
import com.walhalla.data.model.DownloadEntity;
import com.walhalla.data.repository.LocalDatabaseRepo;
import com.walhalla.ui.DLog;

import java.util.List;

public class AutoLoaderAccessibilityService extends AccessibilityService {


    //rss server rssbreadge(insta telega)->pinterest,like
    DefaultUploadHandler[] handlers = new DefaultUploadHandler[]{
            new TikTokUploadHandler()//,new LikeeUploadHandler()
    };


    private PinterestAccessibleUtils accessibleUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        String boardName = "Новости Украины";
        accessibleUtils = new PinterestAccessibleUtils(boardName);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String currentPackageName = String.valueOf(event.getPackageName());


        int eventType = event.getEventType();

        AccessibilityNodeInfo source = event.getSource();


        String eventClazzName = accessibleUtils.getClazzName(event);

        AccessibilityNodeInfo rootNode0 = getRootInActiveWindow();
        String rootNodeClazzName = null;
        if (rootNode0 != null) {
            rootNodeClazzName = accessibleUtils.getClazzName(rootNode0);
            accessibleUtils.prettyPrint(rootNode0, 0);
        }
        if (source == null) {
            accessibleUtils.prettyPrint(event);//TYPE_VIEW_CLICKED or Toast
            DLog.d("==@root=Node@==" + rootNode0);
            DLog.d("==@eventClazzName@==" + eventClazzName + ", " + rootNodeClazzName);
            for (DefaultUploadHandler handler : handlers) {
                if (handler.getPackageName().equals(currentPackageName)) {
                    handler.doStuffWithoutSource(eventClazzName, event, this);
                }
            }
            return;
        }
        DLog.d("@www@" + event.toString());

        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getPackageName().equals("com.whatsapp")) {
                Notification e = (Notification) event.getParcelableData();
                //handleNotify(event.getPackageName().toString(), e);
            } else {

            }
            List<CharSequence> notificationText = event.getText();
            // Отправка данных на сервер
            //sendNotificationToServer(notificationText.toString());
        } else if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            if ("android.widget.FrameLayout".equals(eventClazzName)){
                if(event.toString().contains("Video posted! Everyone can view. Share")){
                    DLog.d( "onAccessibilityEvent: @@@@@@@@posted@@@@@");
                    LocalDatabaseRepo m = LocalDatabaseRepo.getStoreInfoDatabase(getApplicationContext());
                    m.insertPosted(new DownloadEntity(
                            DownloadManagerPresenter.video0.video_id,
                            DownloadManagerPresenter.video0.video_id+".mp4"));
                    new Handler().postDelayed(() ->
                    {
                        doubleBackPressed();
                    }, 2_000);
                }
            }

            for (DefaultUploadHandler handler : handlers) {
                if (handler.getPackageName().equals(currentPackageName)) {
                    handler.doStuff(eventClazzName, event, this);
                }
            }
        } else {
            DLog.d("@@@@@@" + event.toString() + "||" + source);
        }
    }

    private void doubleBackPressed() {
        performGlobalAction(GLOBAL_ACTION_BACK);
        new Handler().postDelayed(() -> {
            performGlobalAction(GLOBAL_ACTION_BACK);
        }, 600);
    }

    @Override
    public void onInterrupt() {
        // Обработка прерывания службы доступности
        Toast.makeText(this, "@@@Interrupt@@", Toast.LENGTH_SHORT).show();
    }


}
