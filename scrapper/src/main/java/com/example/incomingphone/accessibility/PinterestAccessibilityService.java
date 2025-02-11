package com.example.incomingphone.accessibility;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.incomingphone.accessibility.uploadhandler.DefaultUploadHandler;
import com.example.incomingphone.accessibility.uploadhandler.InstagramUploadHandler;
import com.example.incomingphone.accessibility.uploadhandler.LikeeUploadHandler;
import com.example.incomingphone.accessibility.uploadhandler.PinterestUploadHandler;
import com.example.incomingphone.accessibility.uploadhandler.YoutubeUploadHandler;
import com.walhalla.ui.DLog;

public class PinterestAccessibilityService extends AccessibilityService {


    private final PinterestAccessibleUtils accessibleUtils;

    private DefaultUploadHandler[] handlers;


    public PinterestAccessibilityService() {
        accessibleUtils = new PinterestAccessibleUtils();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handlers = new DefaultUploadHandler[]{
                new PinterestUploadHandler(),
                new YoutubeUploadHandler(getApplicationContext()),
                new InstagramUploadHandler(),
                new LikeeUploadHandler()
        };
    }

    //AccessibilityInteractionClient Disconnected nodes.
    //t61.l

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
            accessibleUtils.prettyPrint(event);//TYPE_VIEW_CLICKED
            DLog.d("==@rootNode@==" + rootNode0);
            DLog.d("==@eventClazzName@==" + eventClazzName + ", " + rootNodeClazzName);
            return;
        }

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || eventType == TYPE_WINDOW_CONTENT_CHANGED) {


            boolean resolved = false;

            for (DefaultUploadHandler handler : handlers) {
                if (handler.getPackageName().equals(currentPackageName)) {
                    handler.doStuff(eventClazzName, event, this);
                    resolved = true;
                }
            }
//            if (!resolved) {
//                clickScreen(event);
//            }
        } else if (eventType == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
            DLog.d("@@@@@@" + event.toString() + "||" + source);
        } else if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
            DLog.d("@@@@@@" + event.toString() + "||" + source);
        } else {
            DLog.d("@@@@@@" + event.toString() + "||" + source);
        }
    }


    @Override
    public void onInterrupt() {
        // Обработка прерывания службы доступности
        Toast.makeText(this, "@@@Interrupt@@", Toast.LENGTH_SHORT).show();
    }


    private void doubleTap(AccessibilityNodeInfo targetNode) {

        Rect bounds = new Rect();
        targetNode.getBoundsInScreen(bounds);
        DLog.d("____________________" + bounds);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(bounds.centerX(), bounds.centerY());
            gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 0, 400));
            for (int i = 0; i < 100; i++) {
                dispatchGesture(gestureBuilder.build(), new GestureResultCallback() {
                    @Override
                    public void onCompleted(GestureDescription gestureDescription) {
                        super.onCompleted(gestureDescription);
                        // Действия после завершения жеста
                    }
                }, null);
            }
        }
    }

    private void doRightThenDownDrag() {
        Path dragRightPath = new Path();
        dragRightPath.moveTo(200, 200);
        dragRightPath.lineTo(400, 200);
        long dragRightDuration = 500L; // 0.5 second

        // The starting point of the second path must match
        // the ending point of the first path.
        Path dragDownPath = new Path();
        dragDownPath.moveTo(400, 200);
        dragDownPath.lineTo(400, 400);
        long dragDownDuration = 10_500L;
        GestureDescription.StrokeDescription rightThenDownDrag =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            rightThenDownDrag = new GestureDescription.StrokeDescription(dragRightPath, 0L,
                    dragRightDuration, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            rightThenDownDrag.continueStroke(dragDownPath, dragRightDuration,
                    dragDownDuration, false);
        }
    }

    public void doubleBackPressed() {
        performGlobalAction(GLOBAL_ACTION_BACK);
        new Handler().postDelayed(() -> {
            performGlobalAction(GLOBAL_ACTION_BACK);
        }, 600);
    }
}
