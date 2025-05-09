package com.example.cs360project2weighttracker_jordanmitchell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        authService = new AuthService(this); // Initialize AuthService

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        Button buttonReturn = findViewById(R.id.buttonReturn);

        // Handle Create Account Button Click
        buttonCreateAccount.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                if (authService.registerUser(username, email, password)) {
                    Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    finish(); // Close registration screen
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle Return Button Click
        buttonReturn.setOnClickListener(view -> {
            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            finish();
        });
    }
}
