package com.example.incomingphone.accessibility;


import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK;
import static com.example.incomingphone.accessibility.TelegramUtils.isNotTGIgnore;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.walhalla.ui.DLog;

import java.util.Arrays;
import java.util.List;

public class PinterestAccessibleUtils {

    private static final boolean FOUND_TARGET_MODE = true;

    private final String KEY_BOARD_NAME = "com.pinterest:id/board_name";

    private final String KEY_ERROR_MSG = "com.pinterest:id/text_body";
    private final String KEY_ERROR_DIALOG_BTN_CLOSE = "com.pinterest:id/btn_close";
    private String FOUND_THIS_TITLE = "@@@@";

    public PinterestAccessibleUtils(String string) {
        this.FOUND_THIS_TITLE = string;
    }

    public PinterestAccessibleUtils() {

    }

    public static void handleNodeTree(AccessibilityNodeInfo c0) {
        handleNodeTree(c0, 0);
    }

    public static void handleNodeTree(AccessibilityNodeInfo c0, int currentIndex) {
        if (c0 == null) {
            return;
        }
        String text = (c0.getText() == null) ? "" : c0.getText().toString();
        String contentDescription = (c0.getContentDescription() == null)
                ? "" : c0.getContentDescription().toString();
        String clazzName;
        CharSequence raw = c0.getClassName();
        if (TextUtils.isEmpty(raw)) {
            clazzName = ".....";
        } else {
            clazzName = raw.toString();
        }

        int childCount = c0.getChildCount();
        if (childCount > 0) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


//                if ("android.widget.FrameLayout".equals(clazzName)
//                        && childCount == 3
//                        && contentDescription.isEmpty() && text.isEmpty()
//                        && c0.isClickable() && c0.isImportantForAccessibility()) {
//                    return;
//                }

//@@               if ("android.widget.EditText".equals(clazzName)
//@@                       && c0.isClickable() && c0.isImportantForAccessibility()) {
//@@                   return;
//@@               }

//                if ("android.widget.FrameLayout".equals(clazzName)
//                        && childCount == 3
//                        && contentDescription.isEmpty() && text.isEmpty()
//                        && c0.isClickable() && c0.isImportantForAccessibility()) {
//
//                    AccessibilityNodeInfo mm = c0.getChild(0);
//                    mm.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    return;
//                }

                //@@@   if ("android.widget.FrameLayout".equals(clazzName)
                //@@@           && childCount == 3
                //@@@           && contentDescription.contains("Новости Украины")
                //@@@           && c0.isClickable() && c0.isImportantForAccessibility()) {
                //@@@       c0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //@@@       return;
                //@@@   }

                AccessibilityNodeInfoCompat nodeInfoCompat = AccessibilityNodeInfoCompat.wrap(c0);
                handleItemCompat(nodeInfoCompat, currentIndex);


                for (int i = 0; i < childCount; i++) {

                    AccessibilityNodeInfo child0 = c0.getChild(i);

                    if (!clazzName.equals("android.widget.Toast")) {
                        handleNodeTree(child0, i);
                    }
                }
            }
        } else {
            AccessibilityNodeInfoCompat nodeInfoCompat = AccessibilityNodeInfoCompat.wrap(c0);
            handleItemCompat(nodeInfoCompat, currentIndex);
        }

//        DLog.d(c0.isContextClickable());
//        DLog.d(c0.isEnabled());//+
//        DLog.d(c0.isImportantForAccessibility());//+
//        //c0.getBoundsInScreen();c0.getParent()
//        DLog.d(c0.isPassword());
//        c0.getPackageName() + c0.getClassName()
    }

    private static void handleItemCompat(AccessibilityNodeInfoCompat targetNode, int currentIndex) {

        String clazzName;
        CharSequence raw = targetNode.getClassName();
        if (TextUtils.isEmpty(raw)) {
            clazzName = ".....";
        } else {
            clazzName = raw.toString();
        }
        int childCount = targetNode.getChildCount();


        String text = (targetNode.getText() == null) ? "" : targetNode.getText().toString();
        String contentDescription = (targetNode.getContentDescription() == null)
                ? "" : targetNode.getContentDescription().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            try {
                //targetNode.setDrawingOrder(1);
                //targetNode.setClickable(true);
                //targetNode.setBackgroundColor(Color.YELLOW);
                //targetNode.setText("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            } catch (Exception e) {
                DLog.handleException(e);
            }
            try {
                Rect bounds = new Rect();
                targetNode.getBoundsInScreen(bounds);
                // Добавляем рамку вокруг компонента
                //targetNode.setBoundsInScreen(new Rect(bounds.left - 5, bounds.top - 5, bounds.right + 5, bounds.bottom + 5));

            } catch (Exception e) {
                DLog.handleException(e);
            }
//            try {
//                Bundle arguments = new Bundle();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    //event.getClassName().equals("android.widget.EditText")
//                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "android");
//                    targetNode.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//                }
//            } catch (Exception r) {
//            }


            if (targetNode.isClickable() && targetNode.isImportantForAccessibility()) {

                try {

                    //isTGClicked

                    if (isNotTGIgnore(text, contentDescription)) {
                        DLog.e("@@@@\t \uD83D\uDE80-->" + clazzName + " " + childCount
                                + ", [" + targetNode.isImportantForAccessibility() + ", " + targetNode.isClickable()
                                + "]"
                                + "<" + currentIndex + ">"
                                + text + "@" + contentDescription);
                        targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                } catch (Exception e) {
                    DLog.handleException(e);
                }
            } else {
                DLog.d("@@@@\t \uD83D\uDE80-->" + clazzName + " " + childCount
                        + ", [" + targetNode.isImportantForAccessibility() + ", " + targetNode.isClickable()
                        + "]"
                        + text + "@" + contentDescription);
            }
        }
    }


    private static boolean isTGClicked(String text, String contentDescription) {
        String[] tgIgnoreList = new String[]{
                "Новости Украины", "Новости Украины\n" +
                "                                         9 subscribers"
        };
        List<String> list = Arrays.asList(tgIgnoreList);

        return list.contains(text) || list.contains(contentDescription);
    }

    public void prettyPrint(AccessibilityNodeInfo nodeInfo, int index) {
        if (nodeInfo == null) {
            return;
        }

        String text = (nodeInfo.getText() == null) ? "" : nodeInfo.getText().toString();
        String contentDescription = (nodeInfo.getContentDescription() == null) ? "" : nodeInfo.getContentDescription().toString();

        StringBuilder sb = new StringBuilder();
        sb.append("[*]");

        for (int i = 0; i < index; i++) {
            sb.append("\t");
        }

        String clazzName = getClazzName(nodeInfo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (nodeInfo.isImportantForAccessibility() && nodeInfo.isClickable()) {
                if (FOUND_TARGET_MODE) {
                    sb.append("\uD83D\uDE80-->" + clazzName + " " + nodeInfo.getChildCount()
                            + ", [" + nodeInfo.isImportantForAccessibility() + ", " + nodeInfo.isClickable() + "]" + text + "@" + contentDescription + " " + nodeInfo.toString());

                    DLog.d(sb.toString());
                    clickclack(nodeInfo);
                }

            } else {


                if (text.contains(FOUND_THIS_TITLE) || contentDescription.contains(FOUND_THIS_TITLE)) {

                    AccessibilityNodeInfo parent = nodeInfo.getParent();
                    String getParentClazzName = null;
                    if (parent != null) {
                        getParentClazzName = getClazzName(parent);
                    }
                    sb.append("\t \uD83D\uDE80-->" + clazzName + " " + nodeInfo.getChildCount() + ", ["
                            + nodeInfo.isImportantForAccessibility()
                            + ", " + nodeInfo.isClickable()
                            + ", " + nodeInfo.isCheckable()
                            + ", " + nodeInfo.isEditable()
                            + ", " + nodeInfo.isLongClickable()

                            + "]" + " "
                            + getParentClazzName
                            + "@" + text + "@" + contentDescription + " " + nodeInfo.toString());
                    DLog.e(sb.toString());

                    if (null != parent) {
                        //prettyPrint(parent, index + 1);
                    }
                }

            }

        }

    }

//    public void prettyPrint(AccessibilityNodeInfo nodeInfo, int index) {
//        if (nodeInfo == null) {
//            return;
//        }
//
//        String text = (nodeInfo.getText() == null) ? "" : nodeInfo.getText().toString();
//        String contentDescription = (nodeInfo.getContentDescription() == null) ? "" : nodeInfo.getContentDescription().toString();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("[*]");
//
//        for (int i = 0; i < index; i++) {
//            sb.append("\t");
//        }
//
//        String clazzName = getClazzName(nodeInfo);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//            if (nodeInfo.isImportantForAccessibility() && nodeInfo.isClickable()) {
//                sb.append("\uD83D\uDE80-->" + clazzName + " " + nodeInfo.getChildCount() + ", ["
//                        + nodeInfo.isImportantForAccessibility() + ", " + nodeInfo.isClickable() + "]" + text + "@" + contentDescription + " " + nodeInfo.toString());
//
//                DLog.d(sb.toString());
//            } else {
//
//
//                if (text.contains(FOUND_THIS_TITLE) || contentDescription.contains(FOUND_THIS_TITLE)) {
//
//                    AccessibilityNodeInfo parent = nodeInfo.getParent();
//                    sb.append("\t \uD83D\uDE80-->" + clazzName + " " + nodeInfo.getChildCount() + ", ["
//                            + nodeInfo.isImportantForAccessibility()
//                            + ", " + nodeInfo.isClickable()
//                            + ", " + nodeInfo.isCheckable()
//                            + ", " + nodeInfo.isEditable()
//                            + ", " + nodeInfo.isLongClickable()
//
//                            + "]" + " " + getClazzName(parent) + "@" + text + "@" + contentDescription + " " + nodeInfo.toString());
//                    DLog.e(sb.toString());
//
//                    if (null != parent) {
//                        prettyPrint(parent, index + 1);
//                    }
//                }
//
//            }
//
//        }
//
//    }

    public String getClazzName(AccessibilityNodeInfo nodeInfo) {
        String clazzName;
        CharSequence raw = nodeInfo.getClassName();
        if (TextUtils.isEmpty(raw)) {
            clazzName = ".....";
        } else {
            clazzName = raw.toString();
        }
        return clazzName;
    }

    public String getClazzName(AccessibilityEvent event) {
        String clazzName;
        CharSequence raw = event.getClassName();
        if (TextUtils.isEmpty(raw)) {
            clazzName = ".....";
        } else {
            clazzName = raw.toString();
        }
        return clazzName;
    }

    public void tryFind(AccessibilityNodeInfo rootNode, AccessibilityService service) {

        String[] aa = new String[]{
                KEY_BOARD_NAME, "com.pinterest:id/board_info_wrapper"
        };
        for (String string : aa) {
            List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId(string);
            if (boardNameNodes != null && !boardNameNodes.isEmpty()) {

                for (AccessibilityNodeInfo boardNameNode : boardNameNodes) {
                    prettyPrint(boardNameNode, 4);
                    clickclack(boardNameNode);


                }
//                AccessibilityNodeInfo boardNameNode = boardNameNodes.get(0);
//                clickclack(boardNameNode);

            } else {
                DLog.d("NOT_FOUND: " + string);
            }
        }
    }

    public void clickclack(AccessibilityNodeInfo c0) {
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

                String clazzName = getClazzName(c0);
                String text = (c0.getText() == null) ? null : c0.getText().toString();
                String contentDescription = (c0.getContentDescription() == null) ? "" : c0.getContentDescription().toString();

                if (clazzName.contains("android.widget.EditText")
                        || isCreateBoardButtonDescriptionOrText(contentDescription, text)
                        || isSearch(contentDescription, text)
                        || isClear(contentDescription, text)
                        || isCancelButtonDescription(contentDescription, text)
                        || isFullGroup(contentDescription, text)

                ) {


                    //ignore
                    //                        DLog.d("@@@@\t \uD83D\uDE80-->" + clazzName + " " + c0.getChildCount() + ", " + c0.toString());
//                        DLog.d(c0.isContextClickable());
//                        DLog.d(c0.isEnabled());//+
//                        DLog.d(c0.isImportantForAccessibility());//+
////c0.getBoundsInScreen();c0.getParent()
//                        DLog.d(c0.isPassword());
//                        c0.getPackageName()+c0.getClassName()

                } else {
                    if (c0.isClickable()) {

                        //DLog.d("\t-->" + c0.getClassName() + " " + c0.toString());

                        if (clazzName.equals("androidx.recyclerview.widget.RecyclerView")) {
                            for (int i2 = 0; i2 < c0.getChildCount(); i2++) {

                                DLog.d("RV");
                                AccessibilityNodeInfo node = c0.getChild(i2);
                                prettyPrint(node, 5);

                                try {
                                    //node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                                    clickNode(node);
                                    //doubleTap(node);
                                    //doRightThenDownDrag();
                                } catch (Exception e) {
                                    DLog.handleException(e);
                                }
                            }
                        } else {

                        }
                        try {
                            //c0.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                //DLog.d("@@@@@@@==>" + text + "<==@@@@@@@==>" + contentDescription + ", " + clazzName + ", " + c0);
                            }

                            if (text != null && text.contains("Sorry! Your request could not be completed.")) {

                            }

                            if (clazzName.equals("android.view.View") && text == null) {

                            } else if (clazzName.equals("android.widget.FrameLayout") && text == null) {

                            } else if (clazzName.equals("android.widget.LinearLayout") && FOUND_THIS_TITLE.equals(text)) {
                                DLog.d("@@@@@@@==>" + text + "<==@@@@@@@==>" + contentDescription + ", " + clazzName + ", " + c0);
                                new Handler().postDelayed(() -> c0.performAction(AccessibilityNodeInfo.ACTION_CLICK), 6_000);


                            }

                            else {

                            }


                            //doRightThenDownDrag();
                        } catch (Exception e) {
                            DLog.handleException(e);
                        }

                    }
//                    if (c0.isLongClickable()) {
//                        DLog.d("@@@@@@@L==>" + text + "<==L@@@@@@@");
//                        c0.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
//                    }
                }


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

    private void clickNode(AccessibilityNodeInfo node) {
        String clazzName = getClazzName(node);
        if ("android.widget.LinearLayout".equals(clazzName)
            //&& node.toString().contains(FOUND_THIS_TITLE)

        ) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private boolean isFullGroup(String contentDescription, String text) {
        String var0 = "Search, android, Clear, Cancel, Save to board";
        return (contentDescription != null && contentDescription.contains(var0))
                || (text != null && text.contains(var0));
    }

    private boolean isSearch(String contentDescription, String text) {
        String var0 = "Search";
        return (contentDescription != null && contentDescription.contains(var0))
                || (text != null && text.contains(var0));
    }

    private boolean isClear(String contentDescription, String text) {
        String var0 = "Clear";
        return (contentDescription != null && contentDescription.contains(var0))
                || (text != null && text.contains(var0));
    }

    private boolean isCreateBoardButtonDescriptionOrText(String contentDescription, String text) {
        String var0 = "Create board";
        return (contentDescription != null && contentDescription.contains(var0))
                || (text != null && text.contains(var0));
    }

    private boolean isCancelButtonDescription(String contentDescription, String text) {
        return (contentDescription != null && (contentDescription.contains("Cancel") || contentDescription.contains("Отмена")))
                || (text != null && (text.contains("Cancel") || text.contains("Отмена")));
    }


    public void returnToQuotesApp(AccessibilityService service) {
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        new Handler().postDelayed(() -> service.performGlobalAction(GLOBAL_ACTION_BACK), 10_000);
    }

    public void prettyPrint(AccessibilityEvent event) {
        int type = event.getEventType();
        String label = event.toString();
        if (AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED == (type)) {
            label = "TYPE_WINDOW_CONTENT_CHANGED";
        } else if (AccessibilityEvent.TYPE_VIEW_FOCUSED == (type)) {
            label = "TYPE_VIEW_FOCUSED";
        } else if (AccessibilityEvent.TYPE_VIEW_SCROLLED == (type)) {
            label = "TYPE_VIEW_SCROLLED";
        }
        DLog.d("==@source@==" + label);
    }

    public void tryFindErrorDialog(AccessibilityNodeInfo rootNode, AccessibilityService service) {

        String[] aa = new String[]{
                //KEY_ERROR_MSG,
                KEY_ERROR_DIALOG_BTN_CLOSE
        };
        for (String string : aa) {
            List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId(string);
            if (boardNameNodes != null && !boardNameNodes.isEmpty()) {

                for (AccessibilityNodeInfo boardNameNode : boardNameNodes) {
                    DLog.d("FOUND: " + string + ", " + boardNameNode.isClickable());
                    if (boardNameNode.isClickable()) {
                        //clickclack(boardNameNode);
                        boardNameNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    } else {
                        clickclack(boardNameNode.getParent());
                        clickclack(boardNameNode.getParent().getParent());
                    }
                }
//                AccessibilityNodeInfo boardNameNode = boardNameNodes.get(0);
//                clickclack(boardNameNode);

            } else {
                DLog.d("NOT_FOUND: " + string);
            }
        }
    }
}
