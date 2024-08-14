package com.fit2081.fit2081_a1_parkdarin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fit2081.fit2081_a1_parkdarin.provider.EventCategoryViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NewEventCategoryActivity extends AppCompatActivity {

    EditText etCategoryId;
    EditText etCategoryName;
    EditText etEventCount;
    Switch switchIsActive;
    EditText etCategoryLocation;


    private EventCategoryViewModel mEventCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        etCategoryId = findViewById(R.id.editTextEventCategoryId);
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        switchIsActive = findViewById(R.id.switchIsActive);
        etCategoryLocation = findViewById(R.id.editTextCategoryLocation);

        mEventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
    }

    public void onSaveCategoryButtonClick(View view) {
        String categoryName = etCategoryName.getText().toString().trim();
        String eventCountString = etEventCount.getText().toString().trim();
        boolean isActiveBool = switchIsActive.isChecked();
        String location = etCategoryLocation.getText().toString().trim();

        // Validate Category Name
        if (categoryName.isEmpty() || !categoryName.matches("^[A-Za-z]+[\\w ]*$")) {
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
            return;
        }

        int eventCount;
        if (eventCountString.isEmpty()) {
            eventCount = 0;
            Toast.makeText(this, "Empty Event Count, event count set to default 0", Toast.LENGTH_SHORT).show();
            etEventCount.setText(String.valueOf(eventCount));
        }

        try {
            eventCount = Integer.parseInt(eventCountString);
        } catch (NumberFormatException e) {
            eventCount = 0; // Default to zero if parsing fails
        }

        if (eventCount < 0) {
            Toast.makeText(this, "Invalid Event Count, event count set to default 0", Toast.LENGTH_SHORT).show();
            eventCount = 0; // Default to zero if negative
            etEventCount.setText(String.valueOf(eventCount));
        }

        if(location.isEmpty()){
            location = "";
        } //else if (!isValidAddress(location)){
            //Toast.makeText(this, "Invalid location", Toast.LENGTH_SHORT).show();
            //return;
        //}

        String categoryId = randomCategoryId();
        etCategoryId.setText(categoryId);

        // Save Data if Valid
        EventCategory newCategory = new EventCategory(categoryId, categoryName, eventCount, isActiveBool, location);
        mEventCategoryViewModel.addCategory(newCategory);
        Toast.makeText(this, "Category saved successfully: " + categoryId, Toast.LENGTH_SHORT).show();

        // Redirect to Dashboard Activity
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    public boolean isValidAddress(String addressString) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            // Get list of addresses for the given address string
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);

            // Check if any address found
            if (addresses != null && !addresses.isEmpty()) {
                // Address found, consider it valid
                return true;
            } else {
                // No address found
                return false;
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
            return false;
        }
    }
    public String randomCategoryId() {
        StringBuilder eventId = new StringBuilder(); // Using StringBuilder to build the ID string
        Random random = new Random(); // Random number generator

        eventId.append('C'); // Starting the ID with 'C'
        for (int i = 0; i < 2; i++) {
            eventId.append((char) ('\u0041' + random.nextInt(26))); // Appending two random uppercase letters
            // '\u0041' is the Unicode value for 'A'.
            // Adding a random number between 0 and 25 generates letters A to Z.
        }
        eventId.append('-'); // Adding a hyphen
        for (int i = 0; i < 4; i++) {
            eventId.append((char) ('\u0030' + random.nextInt(10))); // Appending four random digits
            // '\u0030' is the Unicode value for '0'.
            // Adding a random number between 0 and 9 generates digits 0 to 9.
        }
        return String.valueOf(eventId); // Converting StringBuilder to String and returning it
    }
}