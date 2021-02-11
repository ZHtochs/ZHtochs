package com.example.app.ui.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.app.R;
import com.github.zhtouchs.ZHActivityManager;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsUtils {
    private static final int NOTIFICATION_MUSIC_ID = 10000;
    private static final String ChannelName = "NotificationsUtils";
    private static NotificationManager notificationManager;

    //初始化NotificationManager
    public static NotificationManager initNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) ZHActivityManager.INSTANCE.getContext()
                    .getSystemService(NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    //创建通知渠道
    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel() {
        NotificationChannel channel =
                new NotificationChannel(String.valueOf(NOTIFICATION_MUSIC_ID), ChannelName,
                        NotificationManager.IMPORTANCE_DEFAULT);
        //channel有很多set方法
        //为NotificationManager设置通知渠道
        initNotificationManager().createNotificationChannel(channel);
    }

    public static Notification createNotification(PendingIntent intent1, PendingIntent intent2) {
        createNotificationChannel();
        Notification notification =
                new NotificationCompat.Builder(ZHActivityManager.INSTANCE.getContext(),
                        String.valueOf(NOTIFICATION_MUSIC_ID))
                        .setAutoCancel(true)
                        .setContentTitle("收到聊天消息")
                        .setContentText("今天晚上吃什么")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(intent1)
                        .setDeleteIntent(intent2)
                        //在build()方法之前还可以添加其他方法
                        .build();
        return notification;
    }


}
