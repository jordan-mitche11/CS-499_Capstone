package com.example.cs360project2weighttracker_jordanmitchell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSPermissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smspermissions);

        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                return insets;
            });
        }

        // Grant Permission Button opens to notification preferences
        Button buttonGrantPermission = findViewById(R.id.buttonGrantPermission);
        buttonGrantPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSPermissionsActivity.this, NotificationPreferencesActivity.class);
                startActivity(intent);
            }
        });

        // Skip Button goes back
        Button buttonDismiss = findViewById(R.id.buttonDismissPermission);
        buttonDismiss.setOnClickListener(v -> finish());
    }
}
