package com.example.incomingphone.accessibility.uploadhandler;

import static com.example.incomingphone.accessibility.ServiceSettingsActivity.SettingsFragment.pref_boardName;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.incomingphone.accessibility.PinterestAccessibleUtils;
import com.walhalla.abcsharedlib.SharedNetwork;
import com.walhalla.ui.DLog;

public class PinterestUploadHandler extends DefaultUploadHandler implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TASK_LIST_VIEW_CLASS_NAME = "com.example.android.apis.accessibility.TaskListView";

    private static final String ANDROID_TEXTVIEW = "android.widget.TextView";

    private PinterestAccessibleUtils accessibleUtils;
    private String TAG0="@@@";


    public PinterestUploadHandler() {
        super(SharedNetwork.Package.PINTEREST);
    }

    private void initialise(Context context) {
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
    }

    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {
        if ("com.pinterest.activity.create.PinItActivity".equals(eventClazzName)) {
            Handler handler = new Handler();
            handler.postDelayed(() -> clickScreenPinterest(eventClazzName, event, service), 0);//400
        } else if ("com.example.incomingphone.main.PinterestActivity".equals(eventClazzName)) {
            Log.d(TAG0, "Login Screen: ");
        } else if ("com.pinterest.activity.task.activity.MainActivity".equals(eventClazzName)) {
            accessibleUtils.returnToQuotesApp(service);
        } else {
            //org.telegram.ui.Components.BotWebViewSheet
            //android.widget.FrameLayout, com.pinterest
            //androidx.recyclerview.widget.RecyclerView
            clickScreen(event, service);
            clickScreenPinterest(eventClazzName, event, service);
        }
    }

    @Override
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService) {

    }

    private void clickScreenPinterest(String className, AccessibilityEvent event, AccessibilityService service) {
        try {
            DLog.d("=============================================");
            AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
            // Get the source node of the event.
            //AccessibilityNodeInfo rootNode = event.getSource();
            if (rootNode == null) {
                return;
            }


            // Use the event and node information to determine what action to take.

            // Act on behalf of the user.
            //rootNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

            // Recycle the nodeInfo object.
            //nodeInfo.recycle();

            accessibleUtils.tryFindErrorDialog(rootNode, service);
            accessibleUtils.tryFind(rootNode, service);


            //            List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByText(
//                    "100 Inspirational Quotes To Keep You Motivated"
//            );

            //List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId("com.pinterest:id/board_section_picker_board_cell");


        } catch (Exception e) {
            DLog.handleException(e);
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

                        // accessibleUtils.clickclack(c0);

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
//                        // accessibleUtils.clickclack(c0);
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
