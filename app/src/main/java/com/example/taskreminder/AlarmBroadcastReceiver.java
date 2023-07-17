package com.example.taskreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "my_channel_id";
    private static final CharSequence CHANNEL_NAME = "My Channel";
    Ringtone ringtone = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel for Android Oreo and above
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Bundle event = intent.getExtras();
        String title = event.getString("event");

        // Create an Intent for the activity you want to start
        Intent activityIntent = new Intent(context.getApplicationContext(), Alarm.class);
        Bundle bundle = new Bundle();
        bundle.putString("alarmTitle",title);
        activityIntent.putExtra("data",bundle);
        // If activity is already opened, bring it to front
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Task Reminder")
                .setContentText(title)
                .setSmallIcon(R.drawable.alarm)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Dismiss the notification when clicked;

        // Display the notification
        notificationManager.notify(1, notificationBuilder.build());

        // Play the default alarm ringtone
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri != null) {
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
        }

    }

}
