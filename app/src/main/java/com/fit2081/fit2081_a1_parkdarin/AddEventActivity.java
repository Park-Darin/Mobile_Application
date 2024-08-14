package com.fit2081.fit2081_a1_parkdarin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEventActivity extends AppCompatActivity {

    EditText etEventId;
    EditText etEventName;
    EditText etEventCategoryId;
    EditText etTicketsAvailable;
    Switch switchEventIsActive;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        etEventId= findViewById(R.id.editTextEventId);
        etEventName= findViewById(R.id.editTextEventName);
        etEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        etTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);
        switchEventIsActive = findViewById(R.id.switchEventIsActive);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }

    private void saveDataToSharedPreference(String eventId, String eventName, String categoryId, int ticketsAvailable, boolean isActive){
        SharedPreferences sharedPreferences = getSharedPreferences("ADD_EVENT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_EVENT_ID", eventId);
        editor.putString("KEY_EVENT_NAME", eventName);
        editor.putString("KEY_CATEGORY_ID", categoryId);
        editor.putInt("KEY_TICKETS_AVAILABLE", ticketsAvailable);
        editor.putBoolean("KEY_IS_ACTIVE", isActive);
        editor.apply();
    }

    public void onSaveEventButtonClick(View view){
        String eventName = etEventName.getText().toString();
        String categoryId = etEventCategoryId.getText().toString();
        String ticketsAvailable = etTicketsAvailable.getText().toString();
        boolean isActiveBool = switchEventIsActive.isChecked();

        String regex = "^C[A-Z]{2}-\\d{4}$";
        Matcher matcher = Pattern.compile(regex).matcher(categoryId);
        boolean matches = matcher.matches();

        if(matches){
            StringBuilder eventId = new StringBuilder();
            eventId.append('E');
            for (int i = 0; i < 2; i++) {
                eventId.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            eventId.append('-');
            for (int i = 0; i < 5; i++) {
                eventId.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
            }

            if(!eventName.isEmpty() && !categoryId.isEmpty() && !ticketsAvailable.isEmpty()){
                etEventId.setText(eventId);
                saveDataToSharedPreference(eventId.toString(), eventName, categoryId, Integer.parseInt(ticketsAvailable), isActiveBool);
                Toast.makeText(this, "Event saved: "+eventId+" to "+categoryId, Toast.LENGTH_SHORT).show();
            }else if(!eventName.isEmpty() && !categoryId.isEmpty() && ticketsAvailable.isEmpty()){
                etEventId.setText(eventId);
                saveDataToSharedPreference(eventId.toString(), eventName, categoryId, 0, isActiveBool);
                Toast.makeText(this, "Event saved: "+eventId+" to "+categoryId, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "The generated Category Id is not in the correct format.", Toast.LENGTH_SHORT).show();
        }
    }


    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            String[] sT = msg.split(":");
            String check = sT[0];

            if (check.equals("event") && sT.length > 1){
                String[] eventTokens = sT[1].split(";",-1);
                if (eventTokens.length == 4) {
                    String eventName = eventTokens[0];
                    String categoryId = eventTokens[1];
                    String ticketsAvailable = eventTokens[2];
                    String isActive = eventTokens[3].toUpperCase();

                    if (!(eventName.isEmpty()) && !(categoryId.isEmpty())){
                        if (!(ticketsAvailable.isEmpty())){
                            int ticketsAvailableInt;
                            try {
                                ticketsAvailableInt = Integer.parseInt(ticketsAvailable);
                                if (ticketsAvailableInt > 0) {
                                    if (!(isActive.isEmpty())){
                                        if(isActive.equals("TRUE") || isActive.equals("FALSE")){
                                            etEventName.setText(eventName);
                                            etEventCategoryId.setText(categoryId);
                                            etTicketsAvailable.setText(String.valueOf(Integer.parseInt(ticketsAvailable)));
                                            switchEventIsActive.setChecked(isActive.equals("TRUE"));
                                        }else {
                                            Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        etEventName.setText(eventName);
                                        etEventCategoryId.setText(categoryId);
                                        etTicketsAvailable.setText(String.valueOf(Integer.parseInt(ticketsAvailable)));
                                        switchEventIsActive.setChecked(false);
                                    }
                                }else {
                                    Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if (!(isActive.isEmpty())){
                                if(isActive.equals("TRUE") || isActive.equals("FALSE")){
                                    etTicketsAvailable.setText("");
                                    etEventName.setText(eventName);
                                    etEventCategoryId.setText(categoryId);
                                    switchEventIsActive.setChecked(isActive.equals("TRUE"));
                                }else {
                                    Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                etTicketsAvailable.setText("");
                                etEventName.setText(eventName);
                                etEventCategoryId.setText(categoryId);
                                switchEventIsActive.setChecked(false);
                            }
                        }
                    }else {
                        Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}