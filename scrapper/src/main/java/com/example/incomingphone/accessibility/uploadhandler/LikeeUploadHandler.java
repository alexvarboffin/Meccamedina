package com.example.incomingphone.accessibility.uploadhandler;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.incomingphone.accessibility.PinterestAccessibleUtils;
import com.example.incomingphone.accessibility.SharedNetwork;
import com.walhalla.ui.DLog;

import java.util.List;

//5.24.4-6320
//5.24.4-6320

public class LikeeUploadHandler extends DefaultUploadHandler {

    private final PinterestAccessibleUtils utils;

    private final String btnNext = "video.like.produce_record:id/edit_next_text_view";


    private final String btnPublish = "video.like.produce_record:id/post_button_text";

    private final String btnNextNew = "video.like.produce_record:id/tv_next_new";
    //private final String btnPostNew = "video.like.produce_record:id/tv_post_new";

    String[] findme = new String[]{
            btnNext, btnNextNew//, btnPublish//, btnPostNew
    };
    private AccessibilityService service;


    public LikeeUploadHandler() {
        super(SharedNetwork.PACKAGE_LIKEE);
        utils = new PinterestAccessibleUtils("");

    }

    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {
        this.service = service;
        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
        if ("sg.bigo.live.share.receivesharing.SharingActivity".equals(eventClazzName)) {
            //sg.bigo.live
        } else if
        ("com.yy.iheima.startup.MainActivity".equals(eventClazzName)) {

        } else if
        ("video.like.mbh".equals(eventClazzName)) {
//            service.performGlobalAction(GLOBAL_ACTION_BACK);
//            new Handler().postDelayed(() -> {
//                service.performGlobalAction(GLOBAL_ACTION_BACK);
//            }, 400);
        } else if
        ("android.widget.FrameLayout".equals(eventClazzName) && event.getPackageName().toString().contains("video.like")) {

            //video.like.produce_record:id/tv_next_new, true, false@
            tryFindButton(rootNode, service, btnNextNew);
        } else if ("sg.bigo.live.produce.publish.MediaSharePublishActivity".equals(eventClazzName)
                && event.getPackageName().toString().contains("video.like")) {

            //video.like.produce_record:id/tv_next_new, true, false@
            tryFindButton(rootNode, service, btnPublish);
        } else if ("sg.bigo.live.community.mediashare.detail.VideoDetailActivityV2".equals(eventClazzName)
                && event.getPackageName().toString().contains("video.like")) {
        } else if (
            //android.widget.FrameLayout
                event.getPackageName().toString().contains("video.like")
//
//                || "sg.bigo.live.produce.edit.EditorActivity".equals(eventClazzName)
//                ||
//                || "sg.bigo.live.produce.edit".contains(eventClazzName)


        ) {


            new Handler().postDelayed(() ->
            {
                tryFindButtonsRequest(rootNode, service);
            }, 500);
        }


        DLog.d("@_@_@" + eventClazzName + ", ");


        //android.app.Dialog

        handleSuccessToast(event);
//                CharSequence aa = event.getClassName();
//                if (aa == null || aa.length() == 0) {
//
//                } else {
//                    mm = aa.toString();
//                }
        //org.telegram.ui.Components.BotWebViewSheet
        //clickScreen(event, service);
    }

    Handler mHandler = new Handler();

    private void handleSuccessToast(AccessibilityEvent event) {
        String label = event.toString();
        if (label.contains("Загружено! Поделиться") || label.contains("Uploaded! Share on")) {
            DLog.d("-w-" + label);
            mHandler.postDelayed(this::tripleBackPressed, 12_000);
        }
    }

    //ClassName: android.widget.RelativeLayout; Text: [Загружено! Поделиться:]
    @Override
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService) {
        handleSuccessToast(event);
        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
        if ("android.widget.FrameLayout".equals(eventClazzName) && event.getPackageName().toString().contains("video.like")) {

            //video.like.produce_record:id/tv_next_new, true, false@
            tryFindButton(rootNode, service, btnNextNew);
        }
    }

    private void tripleBackPressed() {
        service.performGlobalAction(GLOBAL_ACTION_BACK);
        new Handler().postDelayed(() -> {
            service.performGlobalAction(GLOBAL_ACTION_BACK);
            new Handler().postDelayed(() -> {
                service.performGlobalAction(GLOBAL_ACTION_BACK);
            }, 1_200);
        }, 3_000);
    }

//    private void tripleBackPressed() {
//        service.performGlobalAction(GLOBAL_ACTION_BACK);
//        new Handler().postDelayed(() -> {
//            service.performGlobalAction(GLOBAL_ACTION_BACK);
//            new Handler().postDelayed(() -> {
//                service.performGlobalAction(GLOBAL_ACTION_BACK);
//            }, 1_200);
//        }, 3_000);
//    }

    public void tryFindButtonsRequest(AccessibilityNodeInfo rootNode, AccessibilityService service) {
        for (String string : findme) {
            tryFindButton(rootNode, service, string);
        }
    }

    private void tryFindButton(AccessibilityNodeInfo rootNode, AccessibilityService service, String string) {

        if (rootNode == null) {
            return;
        }

        List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId(string);
        if (boardNameNodes != null && !boardNameNodes.isEmpty()) {

            for (AccessibilityNodeInfo node : boardNameNodes) {
                DLog.d("FOUND: " + string + ", " + node.isClickable() + ", " + node.isLongClickable() + "@" + rootNode.getClassName());
                if (node.isClickable()) {
                    //clickclack(node);
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                } else {
                    if (node.getParent().isClickable()) {
                        clickclack(node.getParent(), string, rootNode);
                    } else {
                        clickclack(node.getParent().getParent(), string, rootNode);
                    }
                }
            }
//                AccessibilityNodeInfo boardNameNode = boardNameNodes.get(0);
//                clickclack(boardNameNode);

        } else {
            //DLog.e("NOT_FOUND: " + string);
        }
    }

    public void clickclack(AccessibilityNodeInfo c0, String currentBtn, AccessibilityNodeInfo rootNode) {
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
                            if (currentBtn.equals(btnNext) || currentBtn.equals(btnNextNew)) {
                                new Handler().postDelayed(() ->
                                {
                                    tryFindButton(rootNode, service, btnPublish);
                                    //tryFindButton(rootNode, service, btnPostNew);
                                }, 2_000);

                            } else if (currentBtn.equals(btnPublish)
                                //||currentBtn.equals(btnPostNew)
                            ) {
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
