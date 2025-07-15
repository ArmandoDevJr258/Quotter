package com.example.quotter;

import static android.view.View.VISIBLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
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
    private CardView  changefontsize;
    private Button btnsave, smallfont, bigfont, mediumfont;
    private int hour, minute;

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

        // Font size change buttons
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

        // Quote of the day switch
        switchQuoteOfTheDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnsave.setVisibility(View.VISIBLE); // show save button
            } else {
                btnsave.setVisibility(View.GONE); // hide save button
                cancelDailyQuoteNotification();   // cancel existing alarm (optional)
            }
        });


        // Save button to set time
        btnsave.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this,
                    (view, hourOfDay, minuteOfHour) -> {
                        hour = hourOfDay;
                        minute = minuteOfHour;

                        // Save time
                        SharedPreferences prefs = getSharedPreferences("quotterPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("quote_hour", hour);
                        editor.putInt("quote_minute", minute);
                        editor.apply();

                        // Set daily notification
                        setDailyQuoteNotification(hour, minute);

                        Toast.makeText(SettingsActivity.this, "Daily Quote set for " + hour + ":" + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
                    }, 12, 0, true);
            timePickerDialog.show();
        });
    }

    private void openActivity(float size) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("fontsize", size);
        startActivity(intent);
    }

    private void setDailyQuoteNotification(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Daily Quote notification canceled", Toast.LENGTH_SHORT).show();
        }
    }

}
