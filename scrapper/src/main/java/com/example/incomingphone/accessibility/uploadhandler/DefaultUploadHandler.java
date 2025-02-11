package com.example.incomingphone.accessibility.uploadhandler;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.walhalla.ui.DLog;

public abstract class DefaultUploadHandler {

    public String getPackageName() {
        return packageName;
    }

    String packageName;

    public DefaultUploadHandler(String packageName) {
        this.packageName = packageName;
    }

    public abstract void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service);

    public abstract void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService);


    protected void traverseNode(AccessibilityNodeInfo node, String hierarchy) {
        if (node == null) {
            return;
        }

        // Добавляем к иерархии текущую ноду
        String currentNodeInfo = (node.getText() != null ? node.getText().toString() : node.getClassName().toString());
        String newHierarchy = hierarchy.isEmpty() ? currentNodeInfo : hierarchy + "->" + currentNodeInfo;

        // Логируем иерархию до текущего элемента
        DLog.d("Node Hierarchy: " + newHierarchy);

        // Рекурсивно обходим дочерние элементы
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            traverseNode(childNode, newHierarchy); // Передаем обновленную иерархию
        }
    }

    protected boolean findNodeByText(AccessibilityNodeInfo node, String uploadedToYourVideos) {
        if (node == null) {
            return false;
        }
        String currentNodeInfo = (node.getText() != null ? node.getText().toString() : node.getClassName().toString());

        // Выводим информацию о текущей ноде (например, текст или класс элемента)
        DLog.d("Node Class: " + node.getClassName() + " | Text: " + currentNodeInfo);
        if ((currentNodeInfo.contains(uploadedToYourVideos))) {
            return true;
        }
        // Рекурсивно обходим дочерние элементы
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            boolean m = findNodeByText(childNode, uploadedToYourVideos);
            if (m) {
                return true;
            }
        }
        return false;
    }

    protected boolean tryNodeClick(AccessibilityNodeInfo targetNode) {
        boolean clicked = targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        String text = (targetNode.getText() == null) ? "" : targetNode.getText().toString();
        if (clicked) {
            DLog.d("Clicked on targetNode with text: " + text);
            return true;
        } else {
            if (!targetNode.isClickable()) {
                DLog.d("Node is not clickable.");
            }
            DLog.d("Failed to click on targetNode with text: " + text + " || ");
        }
        return clicked;
    }

//    private void clickScreen(AccessibilityEvent event, AutoLoaderAccessibilityService service) {
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
