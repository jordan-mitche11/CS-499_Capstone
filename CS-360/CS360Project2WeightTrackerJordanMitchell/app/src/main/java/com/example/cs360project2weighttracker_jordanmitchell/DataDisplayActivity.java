package com.example.cs360project2weighttracker_jordanmitchell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class DataDisplayActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private WeightDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        tableLayout = findViewById(R.id.tableLayout);
        databaseHelper = new WeightDatabaseHelper(this);

        // Load weight history into table
        loadWeightHistory();

        // Initialize Buttons
        Button buttonLogWeight = findViewById(R.id.buttonLogWeight);
        Button buttonViewGoals = findViewById(R.id.buttonViewGoals);
        Button buttonNotifications = findViewById(R.id.buttonNotifications);
        Button buttonReturnHome = findViewById(R.id.buttonReturnHome);

        // Set button click listeners

        // Navigate to Log Weight Screen
        buttonLogWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataDisplayActivity.this, LogWeightActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to Goals Screen
        buttonViewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataDisplayActivity.this, SetGoalsActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to Notifications Screen
        buttonNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataDisplayActivity.this, SMSPermissionsActivity.class);
                startActivity(intent);
            }
        });

        // Navigate back to Main Activity
        buttonReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataDisplayActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close current activity
            }
        });
    }

    private void loadWeightHistory() {
        // Clear previous rows before adding new ones
        tableLayout.removeAllViews();

        // Create LayoutParams to ensure proper alignment
        TableRow.LayoutParams columnLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);

        // Create Header Row
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Create header columns with same weight distribution
        TextView headerDate = new TextView(this);
        headerDate.setText("Date");
        headerDate.setPadding(20, 20, 20, 20);
        headerDate.setTextSize(18);
        headerDate.setTypeface(null, android.graphics.Typeface.BOLD);
        // Apply layout params
        headerDate.setLayoutParams(columnLayoutParams);
        headerDate.setGravity(android.view.Gravity.CENTER);
        headerRow.addView(headerDate);

        TextView headerWeight = new TextView(this);
        headerWeight.setText("Weight");
        headerWeight.setPadding(20, 20, 20, 20);
        headerWeight.setTextSize(18);
        headerWeight.setTypeface(null, android.graphics.Typeface.BOLD);
        // Apply layout parameters
        headerWeight.setLayoutParams(columnLayoutParams);
        headerWeight.setGravity(android.view.Gravity.CENTER);
        headerRow.addView(headerWeight);

        // Add the header row to the table
        tableLayout.addView(headerRow);

        // Retrieve weight data from database
        Cursor cursor = databaseHelper.getAllWeights();
        if (cursor.getCount() == 0) {
            System.out.println("DEBUG: No weight data found in database");
            return;
        }

        // Iterate each row in database and populate table
        while (cursor.moveToNext()) {
            // Retrieve ID from database
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String weight = cursor.getString(cursor.getColumnIndexOrThrow("weight")) + " lbs";

            System.out.println("DEBUG: Retrieved Date: " + date + ", Weight: " + weight);

            // Create a new table row
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Create Date TextView
            TextView dateTextView = new TextView(this);
            dateTextView.setText(date);
            dateTextView.setPadding(20, 20, 20, 20);
            dateTextView.setTextSize(16);
            dateTextView.setGravity(android.view.Gravity.CENTER);
            dateTextView.setLayoutParams(columnLayoutParams);
            row.addView(dateTextView);

            // Create Weight TextView
            TextView weightTextView = new TextView(this);
            weightTextView.setText(weight);
            weightTextView.setPadding(20, 20, 20, 20);
            weightTextView.setTextSize(16);
            weightTextView.setGravity(android.view.Gravity.CENTER);
            weightTextView.setLayoutParams(columnLayoutParams);
            row.addView(weightTextView);

            // Edit Button
            Button editButton = new Button(this);
            editButton.setText("Edit");
            // Pass correct id and weight
            editButton.setOnClickListener(v -> showUpdateDialog(id, weight));
            row.addView(editButton);

            // Delete Button
            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            // Pass correct id
            deleteButton.setOnClickListener(v -> deleteWeightEntry(id));
            row.addView(deleteButton);

            // Add the row to the table
            tableLayout.addView(row);
        }


        cursor.close();
    }
    // Show Update Dialog
    private void showUpdateDialog(int id, String currentWeight) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Weight Entry");

        final EditText input = new EditText(this);
        input.setText(currentWeight.replace(" lbs", ""));
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newWeightStr = input.getText().toString();
            if (!newWeightStr.isEmpty()) {
                double newWeight = Double.parseDouble(newWeightStr);
                boolean updated = databaseHelper.updateWeight(id, newWeight);
                if (updated) {
                    Toast.makeText(DataDisplayActivity.this, "Weight updated successfully", Toast.LENGTH_SHORT).show();
                    loadWeightHistory(); // Refresh table
                } else {
                    Toast.makeText(DataDisplayActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DataDisplayActivity.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Delete Weight Entry
    private void deleteWeightEntry(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this entry?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            boolean deleted = databaseHelper.deleteWeight(id);
            if (deleted) {
                Toast.makeText(DataDisplayActivity.this, "Entry deleted successfully", Toast.LENGTH_SHORT).show();
                loadWeightHistory(); // Refresh table
            } else {
                Toast.makeText(DataDisplayActivity.this, "Failed to delete entry", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
