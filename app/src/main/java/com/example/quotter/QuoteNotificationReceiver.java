package com.example.quotter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class QuoteNotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "quote_channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        // ✅ Show a quick message when the alarm is triggered
        Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_LONG).show();

        // Create notification channel if needed
        createNotificationChannel(context);

        // Prepare intent to launch MainActivity when the user taps the notification
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_quote) // Make sure this icon exists
                .setContentTitle("Quote of the Day")
                .setContentText("Tap to read your daily quote!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(100, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Daily Quote Channel";
            String description = "Channel for daily motivational quotes";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
