package com.fit2081.fit2081_a1_parkdarin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;

    String username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUsername= findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }

    private void saveDataToSharedPreference(String username, String password){
        SharedPreferences sharedPreferences = getSharedPreferences(Key.REGISTER_LOGIN_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key.USERNAME, username);
        editor.putString(Key.PASSWORD, password);
        editor.apply();
    }

    public void onRegisterButtonClick(View view){
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();

        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        } else {
            if(!password.equals(confirmPassword)){
                Toast.makeText(this, "Please ensure password and confirm password fields match.", Toast.LENGTH_SHORT).show();
            } else {
                saveDataToSharedPreference(username, password);
                Toast.makeText(this, "Registration successfully!", Toast.LENGTH_SHORT).show();
                Intent intent_login_activity = new Intent(this, LoginActivity.class);
                startActivity(intent_login_activity);
            }
        }
    }

    public void onLoginButtonClick(View view){
        Intent intent_login_activity = new Intent(this, LoginActivity.class);
        startActivity(intent_login_activity);
    }

}