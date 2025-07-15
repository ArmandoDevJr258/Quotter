package com.example.quotter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {
    public static void showQuoteNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "quote_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Quote Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
               // .setSmallIcon(R.drawable.ic_quote)
                .setContentTitle("Quote of the Day")
                .setContentText("“Stay hungry, stay foolish.” – Steve Jobs") // Replace with random quote logic
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}
