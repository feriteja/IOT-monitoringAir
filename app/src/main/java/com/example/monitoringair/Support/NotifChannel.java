package com.example.monitoringair.Support;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotifChannel extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Sound and Pop up",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Sound and Pop up");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Just Notification",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("Just Notification");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }


}
