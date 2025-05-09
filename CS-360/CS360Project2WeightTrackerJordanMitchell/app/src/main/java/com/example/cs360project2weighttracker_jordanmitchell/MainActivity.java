package com.example.cs360project2weighttracker_jordanmitchell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogWeight, buttonViewHistory, buttonGoals, buttonNotifications, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons
        buttonLogWeight = findViewById(R.id.buttonLogWeight);
        buttonViewHistory = findViewById(R.id.buttonViewHistory);
        buttonGoals = findViewById(R.id.buttonGoals);
        buttonNotifications = findViewById(R.id.buttonNotifications);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Set onClickListeners for screen navigation
        buttonLogWeight.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogWeightActivity.class);
            startActivity(intent);
        });

        buttonViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DataDisplayActivity.class);
            startActivity(intent);
        });

        buttonGoals.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetGoalsActivity.class);
            startActivity(intent);
        });

        buttonNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SMSPermissionsActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
            // Close MainActivity
            finish();
        });
    }
}
