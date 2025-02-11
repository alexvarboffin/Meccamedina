package com.example.incomingphone.accessibility.uploadhandler;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.incomingphone.accessibility.PinterestAccessibilityService;
import com.walhalla.abcsharedlib.SharedNetwork;
import com.walhalla.ui.DLog;


public class InstagramUploadHandler extends DefaultUploadHandler {

    String mm = "com.instagram.modal.TransparentModalActivity";

    public InstagramUploadHandler() {
        super(SharedNetwork.Package.INSTAGRAM);
    }

    @Override
    public void doStuff(String eventClazzName, AccessibilityEvent event, AccessibilityService service) {

        if (mm.equals(eventClazzName)) {

        }
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            clickNextButton(eventClazzName, event, service);


        }, 0);//400
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


            //android.widget.TextView | Text: Next
            AccessibilityNodeInfo node = findNodeByTextContains(rootNode, "Next");
            if (node != null) {
                boolean clicked = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                DLog.e("Clicked " + clicked + " on node with text: " + node.getText());
                if (clicked) {

                } else {
                    boolean t = tryNodeClick(node.getParent());
                    if (!t) {
                        t = tryNodeClick(node.getParent().getParent());
                    }
                    if (!t) {
                        t = tryNodeClick(node.getParent().getParent());
                    }
                }
            } else {
                AccessibilityNodeInfo shareNode = findNodeByTextEqual(rootNode, "Share");
                if (shareNode != null) {
                    boolean clicked = shareNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    DLog.e("Clicked " + clicked + " on shareNode with text: " + shareNode.getText());
                    if (clicked) {

                    } else {
                        boolean t = tryNodeClick(shareNode.getParent());
                        if (!t) {
                            t = tryNodeClick(shareNode.getParent().getParent());
                        }
                        if (!t) {
                            t = tryNodeClick(shareNode.getParent().getParent());
                        }

                        if (t) {
                            //...
                            Handler mHandler = new Handler();
                            CustomTimer customTimer = new CustomTimer();
                            customTimer.start(() -> {

                                AccessibilityNodeInfo rootNode1 = service.getRootInActiveWindow();
                                //AccessibilityNodeInfo sourceNode = event.getSource();

                                if (rootNode1 != null) {

                                    //android.widget.TextView | Text: Finishing up

                                    //!!! Debug !!!
                                    // Обход всех нод в активном окне
                                    //traverseNode(rootNode1, "");

                                    boolean mbb = findNodeByText(rootNode1, "Done posting. Want to send it directly to friends");
                                    DLog.d("Tick-@@-" + mbb);
                                    if (mbb) {

                                        mHandler.postDelayed(() -> {
                                            if (service instanceof PinterestAccessibilityService) {
                                                ((PinterestAccessibilityService) service).doubleBackPressed();
                                            }
                                        }, 2_000);

                                        customTimer.stop();
                                    }
                                }
                            });
                        }
                    }
                } else {

                }
            }

            //            List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByText(
//                    "100 Inspirational Quotes To Keep You Motivated"
//            );

            //List<AccessibilityNodeInfo> boardNameNodes = rootNode.findAccessibilityNodeInfosByViewId("com.pinterest:id/board_section_picker_board_cell");


        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    private AccessibilityNodeInfo findNodeByTextContains(AccessibilityNodeInfo node, String txt) {
        if (node == null) {
            return null;
        }
        String currentNodeInfo = (node.getText() != null ? node.getText().toString() : node.getClassName().toString());

        // Выводим информацию о текущей ноде (например, текст или класс элемента)
        DLog.d("Node Class: " + node.getClassName() + " | Text: " + currentNodeInfo);
        if ((currentNodeInfo.contains(txt))) {
            return node;
        }
        // Рекурсивно обходим дочерние элементы
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            AccessibilityNodeInfo m = findNodeByTextContains(childNode, txt);
            if (m != null) {
                return m;
            }
        }
        return null;
    }

    private AccessibilityNodeInfo findNodeByTextEqual(AccessibilityNodeInfo node, String txt) {
        if (node == null) {
            return null;
        }
        String nodeText = (node.getText() != null ? node.getText().toString() : node.getClassName().toString());

        // Выводим информацию о текущей ноде (например, текст или класс элемента)
        DLog.d("Node Class: " + node.getClassName() + " | Text: " + nodeText);
        if (txt.equals(nodeText)) {
            return node;
        }
        // Рекурсивно обходим дочерние элементы
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            AccessibilityNodeInfo m = findNodeByTextEqual(childNode, txt);
            if (m != null) {
                return m;
            }
        }
        return null;
    }

    @Override
    public void doStuffWithoutSource(String eventClazzName, AccessibilityEvent event, AccessibilityService autoLoaderAccessibilityService) {

    }
}
