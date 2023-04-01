package com.example.pjk.mapd_721_final_project.services;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.pjk.mapd_721_final_project.LoginActivity;
import com.example.pjk.mapd_721_final_project.R;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";
    private static final int NOTIFICATION_ID = 100;
    public static int SECONDS = 0;

    public static final String CHANNEL_ID = "MY_CHANNEL_ID";

    private NotificationManager mNotificationManager;
    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand");
        String seconds = intent.getStringExtra("seconds");
        System.out.println(seconds);
        SECONDS = Integer.parseInt(seconds);
        startForeground(NOTIFICATION_ID, createNotification());
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        stopTimer();
    }

    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Wanderly")
                .setContentText("Notification is now Enabled")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
                .setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel",
                    NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
        }


        return builder.build();
    }

    private void startTimer() {

        System.out.println("TIMER IS = " +SECONDS + " seconds");
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                showNotification();
            }
        }, SECONDS * 1000, SECONDS * 1000);

    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void showNotification() {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Time to Check in!")
                .setContentText("Would you like to checkin now?");

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


