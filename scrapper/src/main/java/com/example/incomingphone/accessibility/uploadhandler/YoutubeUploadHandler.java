package com.example.incomingphone.accessibility.uploadhandler;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
import static com.example.incomingphone.accessibility.ServiceSettingsActivity.SettingsFragment.pref_boardName;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.incomingphone.accessibility.PinterestAccessibilityService;
import com.example.incomingphone.accessibility.PinterestAccessibleUtils;
import com.walhalla.abcsharedlib.SharedNetwork;
import com.walhalla.ui.DLog;

import java.util.List;

public class YoutubeUploadHandler extends DefaultUploadHandler implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TASK_LIST_VIEW_CLASS_NAME = "com.example.android.apis.accessibility.TaskListView";

    private static final String ANDROID_TEXTVIEW = "android.widget.TextView";

    private final PinterestAccessibleUtils accessibleUtils;
    private final Handler handler;
    private AccessibilityService service;


    public YoutubeUploadHandler(Context context) {
        super(SharedNetwork.Package.YOUTUBE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        //String project = sharedPreferences.getString(pref_project, null);
        String boardName = sharedPreferences.getString(pref_boardName, null);
        //MyProject mm = Pinterests.getProjectByName(project);
        if (boardName == null) {
            boardName = "@@@@@@@@@@@@@@@@@@@@@@@@@@";
        }
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@" + boardName + "@@@@@@@@@@@@@@@@");
        accessibleUtils = new PinterestAccessibleUtils(boardName);

        handler = new Handler();
    }


    //TYPE_VIEW_CLICKED; EventTime: 1787507; PackageName: com.google.android.youtube; MovementGranularity: 0; Action: 0; ContentChangeTypes: []; WindowChangeTypes: [] [ ClassName: android.view.ViewGroup; Text: []; ContentDescription: null; ItemCount: -1; CurrentItemIndex: -1; Enabled: true; Password: false; Checked: false; FullScreen: false; Scrollable: false; BeforeText: null; FromIndex: -1; ToIndex: -1; ScrollX: -1; ScrollY: -1; MaxScrollX: -1; MaxScrollY: -1; AddedCount: -1; RemovedCount: -1; ParcelableData: null ]; recordCount: 0||android.view.accessibility.AccessibilityNodeInfo@7820a; boundsInParent: Rect(0, 0 - 504, 54); boundsInScreen: Rect(20, 888 - 520, 942); packageName: com.google.android.youtube; className: android.widget.Button; text: null; error: null; maxTextLength: -1; contentDescription: Upload video; tooltipText: null; viewIdResName: null; checkable: false; checked: false; focusable: true; focused: false; selected: false; clickable: true; longClickable: false; contextClickable: false; enabled: true; password: false; scrollable: false; importantForAccessibility: true; visible: true; actions: [AccessibilityAction: ACTION_FOCUS - null, AccessibilityAction: ACTION_SELECT - null, AccessibilityAction: ACTION_CLEAR_SELECTION - null, AccessibilityAction: ACTION_CLICK - null, AccessibilityAction: ACTION_ACCESSIBILITY_FOCUS - null, AccessibilityAction: ACTION_NEXT_AT_MOVEMENT_GRANULARITY - null, AccessibilityAction: ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY - null, AccessibilityAction: ACTION_SET_SELECTION - null, AccessibilityAction: ACTION_SHOW_ON_SCREEN - null] █

    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {
        this.service = service;

        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
        AccessibilityNodeInfo sourceNode = event.getSource();
//
//        if (rootNode != null) {
//            // Обход всех нод в активном окне
//            traverseNode(rootNode, "");
//        }
//
//        if (sourceNode != null) {
//            // Обрабатываем только ноду, которая вызвала событие
//            DLog.d("Event Source Node: " + sourceNode.getText());
//            // Можем также обойти её дочерние элементы
//            traverseNode(sourceNode,"");
//        }


        if ("com.google.android.apps.youtube.app.extensions.upload.UploadActivity".equals(eventClazzName)) {

            //clickButton(service, "Upload video");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                clickNextButton(eventClazzName, event, service);


            }, 0);//400
//        }
//        else if ("com.example.incomingphone.main.PinterestActivity".equals(eventClazzName)) {
//            Log.d(TAG, "Login Screen: ");
//        } else if ("com.pinterest.activity.task.activity.MainActivity".equals(eventClazzName)) {
//            accessibleUtils.returnToQuotesApp(service);
        } else {
            //org.telegram.ui.Components.BotWebViewSheet
            //android.widget.FrameLayout, com.pinterest
            //androidx.recyclerview.widget.RecyclerView
            //clickScreen(event, service);
            clickNextButton(eventClazzName, event, service);
        }
    }


//    private boolean findNodeByText(AccessibilityService service, String text) {
//
//        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
//        if (rootNode != null) {
//            // Ищем все ноды с нужным текстом
//            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText(text);
//            return nodes != null && !nodes.isEmpty();
//        }
//        DLog.d("Node with text '" + text + "' not found or not clickable.");
//        return false;
//    }

    private boolean clickButton(AccessibilityService service, String text) {

        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
        if (rootNode != null) {
            // Ищем все ноды с нужным текстом
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText(text);

            // Проверяем, есть ли такие ноды
            if (nodes != null && !nodes.isEmpty()) {
                for (AccessibilityNodeInfo node : nodes) {
                    // Пытаемся нажать на каждую найденную ноду
                    if (node.isClickable()) {
                        boolean clicked = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        if (clicked) {
                            DLog.d("Clicked on node with text: " + text);
                            return true; // Останавливаем поиск после первого успешного нажатия
                        }
                    }
                }
            }
        }
        DLog.d("Node with text '" + text + "' not found or not clickable.");
        return false;
    }

    private boolean findAndClickButton(AccessibilityNodeInfo node, String text) {
        if (node == null) {
            return false;
        }
        boolean t = false;
        // Проверяем текст текущей ноды
        CharSequence nodeText = node.getText();
        if (nodeText != null && nodeText.toString().equals(text)) {
            // Если текст совпал, пытаемся нажать на элемент
            boolean clicked = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (clicked) {
                DLog.d("Clicked on node with text: " + text);
                return true;
            } else {
                if (!node.isClickable()) {
                    DLog.d("Node is not clickable.");
                }
                DLog.d("Failed to click on node with text: " + text + " || " + nodeText.toString());
                t = tryNodeClick(node.getParent());
                if (!t) {
                    t = tryNodeClick(node.getParent().getParent());
                }
                if (!t) {
                    t = tryNodeClick(node.getParent().getParent());
                }
            }
        }
        if (t) {
            return true;
        }
        // Рекурсивно обходим дочерние элементы
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            if (findAndClickButton(childNode, text)) {
                return true; // Останавливаем поиск, если уже нажали на кнопку
            }
        }

        return false;
    }

    public enum Screen {
        HOME,
        PROFILE,
        SETTINGS,
        ABOUT
    }

    private Screen currentScreen;

//    public ScreenManager() {
//        // Изначально активен домашний экран
//        this.currentScreen = Screen.HOME;
//    }

    // Получение текущего активного экрана
    public Screen getCurrentScreen() {
        return currentScreen;
    }

    // Установка активного экрана
    public void setCurrentScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen cannot be null");
        }
        this.currentScreen = screen;
        // Можно добавить логику для обработки изменений состояния
        System.out.println("Screen changed to: " + screen);
    }

    // Пример метода для перехода между экранами
    public void goToHome() {
        setCurrentScreen(Screen.HOME);
    }

    public void goToProfile() {
        setCurrentScreen(Screen.PROFILE);
    }

    public void goToSettings() {
        setCurrentScreen(Screen.SETTINGS);
    }

    public void goToAbout() {
        setCurrentScreen(Screen.ABOUT);
    }



    @Override
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService) {

    }

    private void clickNextButton(String className, AccessibilityEvent event, AccessibilityService service) {
        try {

            if ("android.support.v7.widget.RecyclerView".equals(className) &&
                    event.getEventType() == TYPE_WINDOW_CONTENT_CHANGED
            ) {

            }

            DLog.d("0=============================================");
            DLog.d("[" + className + "] [" + event + "]");
            DLog.d("1=============================================");

            AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
            if (rootNode == null) {
                // Get the source node of the event.
                rootNode = event.getSource();
            }

            if (rootNode == null) {
                DLog.d("@@ ROOT NODE NULL @@");
                return;
            }

            // Use the event and node information to determine what action to take.

            // Act on behalf of the user.
            //rootNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

            // Recycle the nodeInfo object.
            //nodeInfo.recycle();

            //accessibleUtils.tryFindErrorDialog(rootNode, service);
            tryFindNextButton(rootNode, service);


            //            List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByText(
//                    "100 Inspirational Quotes To Keep You Motivated"
//            );

            //List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId("com.pinterest:id/board_section_picker_board_cell");


        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    public void tryFindNextButton(AccessibilityNodeInfo rootNode,
                                  AccessibilityService service) {

        String[] aa = new String[]{
                SharedNetwork.Package.YOUTUBE + ":id/upload_menu_button"
        };
        for (String string : aa) {
            List<AccessibilityNodeInfo> infosByViewId = rootNode.findAccessibilityNodeInfosByViewId(string);
            if (infosByViewId != null && !infosByViewId.isEmpty()) {

                for (AccessibilityNodeInfo node : infosByViewId) {
                    if (node != null) {
                        accessibleUtils.prettyPrint(node, 0);
                        String clazzName = accessibleUtils.getClazzName(node);
                        String text = (node.getText() == null) ? null : node.getText().toString();
                        if (clazzName.equals("android.widget.Button") && "Next".equals(text)) {
                            DLog.d("=============>" + node.getText());
                            boolean clicked = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            if (clicked) {
                                DLog.d("Clicked on node with text: " + text);
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(() -> {
                                    AccessibilityNodeInfo window = service.getRootInActiveWindow();
                                    //AccessibilityNodeInfo sourceNode = event.getSource();
                                    boolean m = findAndClickButton(window, "Upload video");
                                    if (m) {
                                        CustomTimer customTimer = new CustomTimer();
                                        customTimer.start(() -> {

                                            AccessibilityNodeInfo rootNode1 = service.getRootInActiveWindow();
                                            //AccessibilityNodeInfo sourceNode = event.getSource();

                                            if (rootNode1 != null) {
                                                boolean mbb = findNodeByText(rootNode1, "Uploaded to Your Videos");
                                                DLog.d("Tick-@@-" + mbb);
                                                if (mbb) {
                                                    mHandler.postDelayed(()->{
                                                        if (service instanceof PinterestAccessibilityService) {
                                                            ((PinterestAccessibilityService) service).doubleBackPressed();
                                                        }
                                                    }, 2_000);

                                                    customTimer.stop();
                                                }
                                            }
                                        });
//                                        // Ждем 5 минут, чтобы увидеть остановку таймера
//                                        Thread.sleep(5 * 60 * 1000);
                                    }
                                    //findAndClickButton(sourceNode, "Upload video");
                                }, 1000);
                                break;
                            } else {
                                if (!node.isClickable()) {
                                    DLog.d("Node is not clickable.");
                                }
                                DLog.d("Failed to click on node with text: " + text + " || " + text);
                                boolean t = tryNodeClick(node.getParent());
                                if (!t) {
                                    t = tryNodeClick(node.getParent().getParent());
                                }
                                if (!t) {
                                    t = tryNodeClick(node.getParent().getParent());
                                }
                            }
                        }
                    }
                }
//                AccessibilityNodeInfo boardNameNode = infosByViewId.get(0);
//                clickclack(boardNameNode);

            } else {
                //DLog.d("NOT_FOUND: " + string);
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

                String clazzName = accessibleUtils.getClazzName(c0);
                String text = (c0.getText() == null) ? null : c0.getText().toString();
                String contentDescription = (c0.getContentDescription() == null) ? "" : c0.getContentDescription().toString();

                if (c0.isClickable()) {

                    //DLog.d("\t-->" + c0.getClassName() + " " + c0.toString());

                    try {
                        //c0.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            //DLog.d("@@@@@@@==>" + text + "<==@@@@@@@==>" + contentDescription + ", " + clazzName + ", " + c0);
                        }


                        if (clazzName.equals("android.widget.Button")) {
                            DLog.d("=============>" + c0.getText());
                            c0.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                        } else if (clazzName.equals("android.view.View") && text == null) {

                        } else if (clazzName.equals("android.widget.FrameLayout") && text == null) {

                        } else {

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

    //com.pinterest.identity.UnauthActivity
    private void clickScreen(AccessibilityEvent event, AccessibilityService service) {

        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
        AccessibilityNodeInfo source = event.getSource();

        if (source == null) {
            return;
        }

        //handleNode(rootNode);
        //handleNode(source);


        // Grab the parent of the view that fires the event.
        AccessibilityNodeInfo rowNode = getListItemNodeInfo(source);//ok
        //AccessibilityNodeInfo rowNode = getListItemNodeInfo(rootNode);//ok

        if (rowNode == null) {
            return;
        }

        // Using this parent, get references to both child nodes, the label, and the
        // checkbox.
        AccessibilityNodeInfo labelNode = rowNode.getChild(0);
        if (labelNode == null) {
            rowNode.recycle();
            return;
        }

        AccessibilityNodeInfo completeNode = rowNode.getChild(1);
        if (completeNode == null) {
            rowNode.recycle();
            return;
        }

        //ss

        // Determine what the task is and whether it's complete based on the text
        // inside the label, and the state of the checkbox.
        if (rowNode.getChildCount() < 2 || !rowNode.getChild(1).isCheckable()) {
            rowNode.recycle();
            return;
        }

        CharSequence taskLabel = labelNode.getText();
        final boolean isComplete = completeNode.isChecked();
        String completeStr = null;

//        if (isComplete) {
//            completeStr = getString(R.string.checked);
//        } else {
//            completeStr = getString(R.string.not_checked);
//        }
//        String reportStr = taskLabel + completeStr;
//        speakToUser(reportStr);

    }

    //    private void traverseNode(AccessibilityNodeInfo node) {
//        if (node == null) {
//            return;
//        }
//
//        // Выводим информацию о текущей ноде (например, текст или класс элемента)
//        DLog.d("Node Class: " + node.getClassName() + " | Text: " + node.getText());
//
//        // Рекурсивно обходим дочерние элементы
//        for (int i = 0; i < node.getChildCount(); i++) {
//            AccessibilityNodeInfo childNode = node.getChild(i);
//            traverseNode(childNode);
//        }
//    }


    private AccessibilityNodeInfo getListItemNodeInfo(AccessibilityNodeInfo source) {
        AccessibilityNodeInfo current = source;
        while (true) {
            AccessibilityNodeInfo parent = current.getParent();//ok

            if (parent == null) {
                DLog.d("<NULL>");
                return null;
            }

            DLog.d("==========================================================================");
            DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + parent.getClassName());
            DLog.d("==========================================================================");

            for (int i = 0; i < parent.getChildCount(); i++) {
                AccessibilityNodeInfo child = parent.getChild(i);
                if (child != null) {
                    accessibleUtils.prettyPrint(child, 1);

                    for (int i1 = 0; i1 < child.getChildCount(); i1++) {
                        AccessibilityNodeInfo c0 = child.getChild(i1);
                        accessibleUtils.prettyPrint(c0, 2);


                        //if (ANDROID_TEXTVIEW.equals(clazzName)) {07071989abba


                        //if (FOUND_THIS_TITLE.equals(text)) {

                        // clickclack(c0);

                        // }
                        //}

                        //android.widget.Button
                    }
                }
            }

            if (TASK_LIST_VIEW_CLASS_NAME.equals(parent.getClassName().toString())) {
                return current;
            }
            // NOTE: Recycle the infos.
            AccessibilityNodeInfo oldCurrent = current;
            current = parent;
            oldCurrent.recycle();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable @org.jetbrains.annotations.Nullable String key) {
        DLog.d("{key}" + key);
    }

//    public AccessibilityNodeInfo getListItemNodeInfo(AccessibilityNodeInfo source) {
//        AccessibilityNodeInfo current = source;
//        while (true) {
//            AccessibilityNodeInfo parent = current.getParent();//ok
//
//            if (parent == null) {
//                DLog.d("<NULL>");
//                return null;
//            }
//
//            DLog.d("==========================================================================");
//            DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + parent.getClassName());
//            DLog.d("==========================================================================");
//
//            for (int i = 0; i < parent.getChildCount(); i++) {
//                AccessibilityNodeInfo child = parent.getChild(i);
//                if (child != null) {
//                    accessibleUtils.prettyPrint(child, 1);
//
//                    for (int i1 = 0; i1 < child.getChildCount(); i1++) {
//                        AccessibilityNodeInfo c0 = child.getChild(i1);
//                        accessibleUtils.prettyPrint(c0, 2);
//
//
//                        //if (ANDROID_TEXTVIEW.equals(clazzName)) {07071989abba
//
//
//                        //if (FOUND_THIS_TITLE.equals(text)) {
//
//                        // clickclack(c0);
//
//                        // }
//                        //}
//
//                        //android.widget.Button
//                    }
//                }
//            }
//
//            if (TASK_LIST_VIEW_CLASS_NAME.equals(parent.getClassName().toString())) {
//                return current;
//            }
//            // NOTE: Recycle the infos.
//            AccessibilityNodeInfo oldCurrent = current;
//            current = parent;
//            oldCurrent.recycle();
//        }
//    }
}
