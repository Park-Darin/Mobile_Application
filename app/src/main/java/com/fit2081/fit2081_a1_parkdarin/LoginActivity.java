package com.fit2081.fit2081_a1_parkdarin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginUsername;
    EditText etLoginPassword;

    String enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(Key.REGISTER_LOGIN_DATA, MODE_PRIVATE);
        String usernameRestored = sharedPreferences.getString(Key.USERNAME, "");
        etLoginUsername = findViewById(R.id.editTextLoginUsername);
        etLoginUsername.setText(usernameRestored);
        etLoginPassword = findViewById(R.id.editTextLoginPassword);
    }

    public void onAnotherLoginButtonClick(View view){
        enteredPassword = etLoginPassword.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(Key.REGISTER_LOGIN_DATA, MODE_PRIVATE);
        String passwordRestored = sharedPreferences.getString(Key.PASSWORD, "DEFAULT VALUE");


        if(enteredPassword.equals(passwordRestored)){
            Toast.makeText(this, "Login successfully.", Toast.LENGTH_SHORT).show();
            Intent intent_dashboard_activity = new Intent(this, DashboardActivity.class);
            startActivity(intent_dashboard_activity);
        }else {
            Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onAnotherRegisterButtonClick(View view){
        Intent intent_register_activity = new Intent(this, RegisterActivity.class);
        startActivity(intent_register_activity);
    }
}