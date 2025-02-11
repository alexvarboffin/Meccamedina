package com.example.incomingphone.accessibility.uploadhandler;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.demo.scrapper.ytshorts.downloader.DownloadManagerPresenter;
import com.example.incomingphone.accessibility.PinterestAccessibleUtils;
import com.example.incomingphone.accessibility.SharedNetwork;
import com.walhalla.data.model.DownloadEntity;
import com.walhalla.data.repository.LocalDatabaseRepo;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TikTokUploadHandler extends DefaultUploadHandler {

    private final PinterestAccessibleUtils utils;

    private final String[] btnNext = new String[]{
            "com.zhiliaoapp.musically:id/i8i",
            "com.zhiliaoapp.musically:id/iec",
            "com.zhiliaoapp.musically:id/jns",

    };


    private final String[] btnPublish = new String[]{
            "com.zhiliaoapp.musically:id/jxi",
            "com.zhiliaoapp.musically:id/k54",
            "com.zhiliaoapp.musically:id/ln2"
    };

    List<String> findme = new ArrayList<>();


    private AccessibilityService service;
    private AccessibilityNodeInfo rootNode;


    public TikTokUploadHandler() {
        super(SharedNetwork.PACKAGE_TIKTOK_M_PACKAGE);
        utils = new PinterestAccessibleUtils("");


        findme.addAll(Arrays.asList(btnNext));
        findme.addAll(Arrays.asList(btnPublish));

    }
    private void doubleBackPressed() {
        service.performGlobalAction(GLOBAL_ACTION_BACK);
        new Handler().postDelayed(() -> {
            service.performGlobalAction(GLOBAL_ACTION_BACK);
        }, 600);
    }
    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {
        this.service = service;
        this.rootNode = service.getRootInActiveWindow();

        if ("com.ss.android.ugc.aweme.share.SystemShareActivity".equals(eventClazzName)
                || "com.ss.android.ugc.aweme.adaptation.saa.SAAActivity".equals(eventClazzName)

        ) {

            tryFindButtonsRequest(rootNode, service);
        }


        DLog.d("@@@@@@@@@@@@@@@@@" + eventClazzName);
//                CharSequence aa = event.getClassName();
//                if (aa == null || aa.length() == 0) {
//
//                } else {
//                    mm = aa.toString();
//                }
        //org.telegram.ui.Components.BotWebViewSheet
        //clickScreen(event, service);
    }


    @Override
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService) {
        String label = event.toString();
        if (label.contains("Video posted. You can now share it")) {
            DLog.d("---------------------------------->" + label);
            LocalDatabaseRepo m = LocalDatabaseRepo.getStoreInfoDatabase(autoLoaderAccessibilityService.getApplicationContext());
            m.insertPosted(new DownloadEntity(
                    DownloadManagerPresenter.video0.video_id,
                    DownloadManagerPresenter.video0.video_id+".mp4"));
            new Handler().postDelayed(() ->
            {
               doubleBackPressed();
            }, 2_000);
        }
    }

    public void tryFindButtonsRequest(AccessibilityNodeInfo rootNode, AccessibilityService service) {
        for (String string : findme) {
            tryFindButton(rootNode, service, string);
        }
    }

    private void tryFindButton(AccessibilityNodeInfo rootNode, AccessibilityService service, String string) {
        List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId(string);
        if (boardNameNodes != null && !boardNameNodes.isEmpty()) {

            for (AccessibilityNodeInfo node : boardNameNodes) {
                DLog.d("FOUND: " + string + ", " + node.isClickable() + ", " + node.isLongClickable());
                if (node.isClickable()) {
                    //clickclack(node);
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                } else {
                    AccessibilityNodeInfo p0 = node.getParent();
                    if (p0.isClickable()) {
                        clickclack(p0, string);
                    } else {
                        clickclack(p0.getParent(), string);
                    }
                }
            }
//                AccessibilityNodeInfo boardNameNode = boardNameNodes.get(0);
//                clickclack(boardNameNode);

        } else {
            DLog.d("NOT_FOUND: " + string);
        }
    }

    public void clickclack(AccessibilityNodeInfo c0, String currentBtn) {
        if (c0 != null) {

            try {
                //DLog.d("// Нажать на узел " + c0.isClickable());
                //c0.addAction(AccessibilityNodeInfo.ACTION_CLICK);
                int mm = c0.getActions();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

//                            List<AccessibilityNodeInfo.AccessibilityAction> mm0 = c0.getActionList();
//                            for (AccessibilityNodeInfo.AccessibilityAction action : mm0) {
//                                DLog.d("<A>" + action);
//                            }
//                            boolean rr = mm0.contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
//                            if (rr) {
//                            }
//                            DLog.d("@@@" + rr);
                    boolean importantForAccessibility = c0.isImportantForAccessibility();
//                    if (!importantForAccessibility) {
//                        try {
//                            c0.setImportantForAccessibility(true);
//                        } catch (Exception e) {
//                            DLog.handleException(e);
//                        }
//                    }
                }
                String clazzName = utils.getClazzName(c0);
                String text = (c0.getText() == null) ? null : c0.getText().toString();
                String contentDescription = (c0.getContentDescription() == null) ? "" : c0.getContentDescription().toString();

                if (c0.isClickable()) {

                    DLog.d("\t-->" + c0.getClassName() + " " + c0.toString());

                    try {
                        //c0.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            DLog.d("@@@@@@@==>" + text + "<==@@@@@@@==>" + contentDescription + ", " + clazzName + ", " + c0);
                        }
                        new Handler().postDelayed(() -> {
                            c0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            if (List.of(btnNext).contains(currentBtn)) {
                                new Handler().postDelayed(() ->
                                {
                                    for (String publish : btnPublish) {
                                        tryFindButton(rootNode, service, publish);
                                    }
                                    //tryFindButton(rootNode, service, btnPublish2);
                                }, 2_000);

                            } else if (List.of(btnPublish).contains(currentBtn)) {
                                new Handler().postDelayed(() ->
                                {
                                    //doubleBackPressed();
                                }, 30_000);

                            }

                        }, 1_000);


                        //doRightThenDownDrag();
                    } catch (Exception e) {
                        DLog.handleException(e);
                    }

                }
//                    if (c0.isLongClickable()) {
//                        DLog.d("@@@@@@@L==>" + text + "<==L@@@@@@@");
//                        c0.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
//                    }


//                        c0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        c0.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
//                        c0.performAction(AccessibilityNodeInfo.ACTION_SELECT);


                Bundle arguments = new Bundle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //event.getClassName().equals("android.widget.EditText")
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "android");
                    c0.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                }

                //rootNode.recycle();
                //DLog.d("000");
            } catch (Exception e) {
                DLog.handleException(e);
            }


//                    if (c0.isClickable()) {
//                        try {
//                            c0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        } catch (Exception e) {
//                            DLog.handleException(e);
//                        }
//                    } else {
//                        AccessibilityNodeInfo p = c0.getParent();
//                        if (p.isClickable()) {
//                            DLog.d("// Нажать " + p.toString() + ", " + p.isClickable());
//                            try {
//                                p.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            } catch (Exception e) {
//                                DLog.handleException(e);
//                            }
//                        }
//                    }
        } else {
            DLog.d("noneee");
        }
    }



    

}
