package com.example.incomingphone.accessibility.uploadhandler;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.example.incomingphone.accessibility.SharedNetwork;
import com.walhalla.ui.DLog;

public class TelegramUploadHandler extends DefaultUploadHandler {

    public TelegramUploadHandler() {
        super(SharedNetwork.PCG_TELEGRAM);
    }

    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {
        if("org.telegram.ui.LaunchActivity".equals(eventClazzName)){
            DLog.d("@@@@@@@@@@@@@@@@@");
        }

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
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService AccessibilityService) {

    }

//    private void clickScreen(AccessibilityEvent event, AccessibilityService service) {
//        AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
//        AccessibilityNodeInfo source = event.getSource();
//        handleNodeTree(rootNode);
//        handleNodeTree(source);
//
//        if (source == null) {
//            return;
//        }
//
//        // Grab the parent of the view that fires the event.
//        //AccessibilityNodeInfo rowNode = getListItemNodeInfo(source);//ok
//        AccessibilityNodeInfo rowNode = getListItemNodeInfo(rootNode);//ok
//
//        if (rowNode == null) {
//            return;
//        }
//
//        // Using this parent, get references to both child nodes, the label, and the
//        // checkbox.
//        AccessibilityNodeInfo labelNode = rowNode.getChild(0);
//        if (labelNode == null) {
//            rowNode.recycle();
//            return;
//        }
//
//        AccessibilityNodeInfo completeNode = rowNode.getChild(1);
//        if (completeNode == null) {
//            rowNode.recycle();
//            return;
//        }
//
//        //ss
//
//        // Determine what the task is and whether it's complete based on the text
//        // inside the label, and the state of the checkbox.
//        if (rowNode.getChildCount() < 2 || !rowNode.getChild(1).isCheckable()) {
//            rowNode.recycle();
//            return;
//        }
//
//        CharSequence taskLabel = labelNode.getText();
//        final boolean isComplete = completeNode.isChecked();
//        String completeStr = null;
//
////        if (isComplete) {
////            completeStr = getString(R.string.checked);
////        } else {
////            completeStr = getString(R.string.not_checked);
////        }
////        String reportStr = taskLabel + completeStr;
////        speakToUser(reportStr);
//    }


}
