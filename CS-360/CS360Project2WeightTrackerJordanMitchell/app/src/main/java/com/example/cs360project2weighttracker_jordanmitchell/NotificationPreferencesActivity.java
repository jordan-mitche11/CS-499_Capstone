package com.example.cs360project2weighttracker_jordanmitchell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationPreferencesActivity extends AppCompatActivity {

    private CheckBox checkBoxDailyReminders, checkBoxGoalReminders, checkBoxAchievements;
    private Button buttonSavePreferences, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_preferences);

        // Initialize UI elements
        checkBoxDailyReminders = findViewById(R.id.checkBoxDailyReminders);
        checkBoxGoalReminders = findViewById(R.id.checkBoxGoalReminders);
        checkBoxAchievements = findViewById(R.id.checkBoxAchievements);
        buttonSavePreferences = findViewById(R.id.buttonSavePreferences);
        buttonBack = findViewById(R.id.buttonBack);

        // Handle Save Preferences button click
        buttonSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationPreferences();
            }
        });

        // Handle Back button click
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and return
            }
        });
    }

    private void saveNotificationPreferences() {
        StringBuilder preferences = new StringBuilder("SMS notifications enabled for: ");

        if (checkBoxDailyReminders.isChecked()) {
            preferences.append("\n- Daily Reminders");
        }
        if (checkBoxGoalReminders.isChecked()) {
            preferences.append("\n- Goal Reminders");
        }
        if (checkBoxAchievements.isChecked()) {
            preferences.append("\n- Achievement Notifications");
        }

        if (!checkBoxDailyReminders.isChecked() && !checkBoxGoalReminders.isChecked() && !checkBoxAchievements.isChecked()) {
            preferences = new StringBuilder("No notifications enabled.");
        }

        // Display confirmation
        Toast.makeText(this, preferences.toString(), Toast.LENGTH_LONG).show();
    }
}
