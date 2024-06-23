package com.example.engelsizrehber;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import android.util.Log;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onCreate() {
        super.onCreate();
        // Tüm cihazları "genel" konusuna abone et
        FirebaseMessaging.getInstance().subscribeToTopic("genel")
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed to 'genel' topic";
                    if (!task.isSuccessful()) {
                        msg = "Subscription to 'genel' topic failed";
                    }
                    Log.d(TAG, msg);
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Bildirimleri burada işleyin
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            // Bildirimi gösterin veya işleyin
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // Yeni token'ı sunucunuza gönderin veya saklayın
    }
}
