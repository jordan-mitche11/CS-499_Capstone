package com.example.cs360project2weighttracker_jordanmitchell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogWeightActivity extends AppCompatActivity {

    private EditText editTextWeight;
    private WeightDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_weight);

        // Initialize UI
        editTextWeight = findViewById(R.id.editTextWeight);
        Button buttonSaveWeight = findViewById(R.id.buttonSaveWeight);
        Button buttonBack = findViewById(R.id.buttonBack); // Find Back Button

        // Initialize Database
        databaseHelper = new WeightDatabaseHelper(this);

        // Listen for Save Weight button
        buttonSaveWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWeight();
            }
        });

        // Listen for Back Button press
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close activity and return to previous
                finish();
            }
        });
    }

    private void saveWeight() {
        String weightStr = editTextWeight.getText().toString();

        if (weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter a weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        boolean success = databaseHelper.insertWeight(currentDate, weight);

        if (success) {
            Toast.makeText(this, "Weight logged successfully", Toast.LENGTH_SHORT).show();
            editTextWeight.setText(""); // Clear input field
        } else {
            Toast.makeText(this, "Failed to log weight", Toast.LENGTH_SHORT).show();
        }
    }
}
