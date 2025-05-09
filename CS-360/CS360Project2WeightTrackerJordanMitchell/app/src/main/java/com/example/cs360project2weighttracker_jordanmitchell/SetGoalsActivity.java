package com.example.cs360project2weighttracker_jordanmitchell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetGoalsActivity extends AppCompatActivity {

    private EditText editTextCurrentWeight, editTextWeightGoal, editTextTargetDate;
    private TextView textGoalSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets;
        });

        // Initialize UI
        editTextCurrentWeight = findViewById(R.id.editTextCurrentWeight);
        editTextWeightGoal = findViewById(R.id.editTextWeightGoal);
        editTextTargetDate = findViewById(R.id.editTextTargetDate);
        textGoalSummary = findViewById(R.id.textGoalSummary);
        Button buttonSubmitGoal = findViewById(R.id.buttonSubmitGoal);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Handle "Save Goal" button click
        buttonSubmitGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });

        // Handle "Back" button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close activity and return
                finish();
            }
        });
    }

    private void saveGoal() {
        String currentWeight = editTextCurrentWeight.getText().toString().trim();
        String goalWeight = editTextWeightGoal.getText().toString().trim();
        String targetDate = editTextTargetDate.getText().toString().trim();

        // Ensure all fields are populated
        if (currentWeight.isEmpty() || goalWeight.isEmpty() || targetDate.isEmpty()) {
            textGoalSummary.setText("Please enter all fields to set a goal.");
            return;
        }

        // Update Goal display TextView
        String goalSummary = "Your current goal is to reach " + goalWeight + " lbs by " + targetDate;
        textGoalSummary.setText(goalSummary);
    }
}
