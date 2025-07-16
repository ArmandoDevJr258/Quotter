package com.example.quotter;

import static android.view.View.VISIBLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications, switchQuoteOfTheDay;
    private Button fontsize;
    private CardView changefontsize;
    private Button smallfont, bigfont, mediumfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        switchNotifications = findViewById(R.id.switchNotifications);
        switchQuoteOfTheDay = findViewById(R.id.switchQuoteOfTheDay);
        fontsize = findViewById(R.id.fontsize);

        changefontsize = findViewById(R.id.changefontsize);
        smallfont = findViewById(R.id.smallfont);
        mediumfont = findViewById(R.id.mediumfont);
        bigfont = findViewById(R.id.bigfont);

        // Font size change
        fontsize.setOnClickListener(v -> changefontsize.setVisibility(VISIBLE));
        smallfont.setOnClickListener(v -> {
            openActivity(14f);
            fontsize.setText("small");
        });
        mediumfont.setOnClickListener(v -> {
            openActivity(18f);
            fontsize.setText("Medium");
        });
        bigfont.setOnClickListener(v -> {
            openActivity(24f);
            fontsize.setText("big");
        });

        // Switch for Quote of the Day
        switchQuoteOfTheDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setDailyQuoteNotification(); // Automatically set for 8 AM
                Toast.makeText(this, "Daily Quote enabled for 8:00 AM", Toast.LENGTH_SHORT).show();
            } else {
                cancelDailyQuoteNotification();
                Toast.makeText(this, "Daily Quote notification canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivity(float size) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("fontsize", size);
        startActivity(intent);
    }

    private void setDailyQuoteNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);

        // If time has passed today, schedule for tomorrow
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, QuoteNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    private void cancelDailyQuoteNotification() {
        Intent intent = new Intent(this, QuoteNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
