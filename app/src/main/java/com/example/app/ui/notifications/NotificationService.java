package com.example.app.ui.notifications;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.github.zhtouchs.Utils.ZHLog;

import java.security.SecureRandom;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ZHLog.d(TAG, "onStartCommand" + this);
        PendingIntent intent1 = PendingIntent.getActivity(this, 0,
                new Intent(), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent intent2 = PendingIntent.getActivity(this, 0,
                new Intent(), PendingIntent.FLAG_IMMUTABLE);
        int id = new SecureRandom().nextInt();
        ZHLog.d(TAG, "id " + id);
        startForeground(id,
                NotificationsUtils.createNotification(
                        intent1, intent2));
        stopForeground(STOP_FOREGROUND_DETACH);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ZHLog.d(TAG, "onDestroy");
        super.onDestroy();
    }
}