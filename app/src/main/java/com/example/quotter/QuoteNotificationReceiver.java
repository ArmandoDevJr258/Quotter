package com.example.quotter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.quotter.NotificationUtils;

public class QuoteNotificationReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.showQuoteNotification(context); // you define this
    }
}
