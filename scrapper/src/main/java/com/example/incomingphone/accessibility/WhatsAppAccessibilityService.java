package com.example.incomingphone.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;

public class WhatsAppAccessibilityService extends AccessibilityService {

    String sms = "com.google.android.apps.messaging";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        System.out.println("@@@@@@" + event.toString());

        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getPackageName().equals("com.whatsapp")) {
                Notification e = (Notification) event.getParcelableData();
                handleNotify(event.getPackageName().toString(), e);
            } else {

            }
            List<CharSequence> notificationText = event.getText();
            // Отправка данных на сервер
            sendNotificationToServer(notificationText.toString());
        }
    }

    private void sendNotificationToServer(String string) {
    }

    @Override
    protected void onServiceConnected() {
        System.out.println("onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.notificationTimeout = 100;
        info.feedbackType = AccessibilityEvent.TYPES_ALL_MASK;
        setServiceInfo(info);
    }

    private void handleNotify(String packageName, Notification e) {
        // handle notification for packageName

    }

    @Override
    public void onInterrupt() {
        // Обработка прерывания службы доступности
        Toast.makeText(this, "@@@Interrupt@@", Toast.LENGTH_SHORT).show();
    }
}

