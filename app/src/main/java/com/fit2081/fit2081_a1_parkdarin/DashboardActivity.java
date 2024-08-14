package com.fit2081.fit2081_a1_parkdarin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.fit2081_a1_parkdarin.provider.EventCategoryViewModel;
import com.fit2081.fit2081_a1_parkdarin.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerlayout;

    EditText etEventId;
    EditText etEventName;
    EditText etEventCategoryId;
    EditText etTicketsAvailable;
    Switch switchEventIsActive;
    TextView tvGesture;
    View touchPad;
    private EventCategoryViewModel mEventCategoryViewModel;
    private EventViewModel mEventViewModel;
    private GestureDetectorCompat mDetector;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_app_bar_layout);

        etEventId= findViewById(R.id.dashboardEventId);
        etEventName= findViewById(R.id.dashboardEventName);
        etEventCategoryId = findViewById(R.id.dashboardEventCategoryId);
        etTicketsAvailable = findViewById(R.id.dashboardTicketsAvailable);
        switchEventIsActive = findViewById(R.id.switchDashboardEventIsActive);
        tvGesture = findViewById(R.id.tv_gesture);

        drawerlayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("A3_ParkDarin");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mDetector = new GestureDetectorCompat(this, customGestureDetector);
        mDetector.setOnDoubleTapListener(customGestureDetector);
        touchPad = findViewById(R.id.view_touchpad);
        touchPad.setOnTouchListener((v, event) -> {
            mDetector.onTouchEvent(event);
            return true;
        });
        refreshCategories();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            save(new SaveEventCallback() {
                @Override
                public void onSaveSuccess(String eventId) {
                    Snackbar snackbar = Snackbar.make(touchPad, "New Event Saved", Snackbar.LENGTH_LONG).setAction("UNDO", v -> {
                        undoAction(eventId);
                        refreshCategories();
                    });
                    snackbar.show();
                }
                @Override
                public void onSaveFailure() {
                }
            });
        });
    }

    private void undoAction(String eventId) {
        mEventViewModel.deleteEvent(eventId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_view_category) {
            startActivity(new Intent(DashboardActivity.this, ListCategoryActivity.class));
        } else if (item.getItemId() == R.id.item_add_category) {
            startActivity(new Intent(DashboardActivity.this, NewEventCategoryActivity.class));
        } else if (item.getItemId() == R.id.item_view_event) {
            startActivity(new Intent(DashboardActivity.this, ListEventActivity.class));
        } else if (item.getItemId() == R.id.item_logout){
            logout();
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_refresh) {
            refreshCategories();
        } else if (item.getItemId() == R.id.item_clear_form) {
            clearFields();
        } else if (item.getItemId() == R.id.item_delete_categories) {
            mEventCategoryViewModel.deleteAllCategories();
        } else if (item.getItemId() == R.id.item_delete_events) {
            mEventViewModel.deleteAllEvents();
        }
        return true;
    }

    public void refreshCategories(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new FragmentListCategory()).commit();
    }
    public void clearFields(){
        etEventId.setText("");
        etEventName.setText("");
        etEventCategoryId.setText("");
        etTicketsAvailable.setText("");
        switchEventIsActive.setChecked(false);
    }

    public String randomEventId(){
        StringBuilder eventId = new StringBuilder();
        Random random = new Random();

        eventId.append('E');
        for (int i = 0; i < 2; i++) {
            eventId.append((char) ('\u0041' + random.nextInt(26))); // Unicode for 'A' is U+0041
        }
        eventId.append('-');
        for (int i = 0; i < 5; i++) {
            eventId.append((char) ('\u0030' + random.nextInt(10))); // Append digit using Unicode value
        }
        return String.valueOf(eventId);
    }

    public interface SaveEventCallback {
        void onSaveSuccess(String eventId);
        void onSaveFailure();
    }


    public void save(SaveEventCallback callback) {
        String eventName = etEventName.getText().toString();
        String eventCategoryId = etEventCategoryId.getText().toString();
        String ticketsAvailable = etTicketsAvailable.getText().toString();
        boolean eventActive = switchEventIsActive.isChecked();
        String eventId = randomEventId();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Check whether the required field is filled up or not
        if (eventName.isEmpty() || eventCategoryId.isEmpty()) {
            Toast.makeText(DashboardActivity.this, "Please fill up the required fields", Toast.LENGTH_SHORT).show();
            callback.onSaveFailure();
            return;
        }

        executor.execute(() -> {
            // Check the event name is valid or not
            if (!eventName.matches("^[A-Za-z]+[\\w ]*$")) {
                uiHandler.post(() -> {
                    Toast.makeText(DashboardActivity.this, "Invalid event name", Toast.LENGTH_SHORT).show();
                    callback.onSaveFailure();
                });
                return;
            }

            // Check the given categoryId is in the database or not
            List<EventCategory> categories = mEventCategoryViewModel.getCategorybyId(eventCategoryId);
            if (categories.isEmpty()) {
                uiHandler.post(() -> {
                    Toast.makeText(DashboardActivity.this, "Category does not exist", Toast.LENGTH_SHORT).show();
                    callback.onSaveFailure();
                });
                return;
            }

            EventCategory eventCategory = categories.get(0);

            int ticketCount;
            // Check the tickets input if it's not valid put the default value which is 0
            if (ticketsAvailable.isEmpty()) {
                ticketCount = 0;
                uiHandler.post(() -> {
                    Toast.makeText(DashboardActivity.this, "Empty Tickets input, tickets set to default 0", Toast.LENGTH_SHORT).show();
                    runOnUiThread(() -> etTicketsAvailable.setText(String.valueOf(0)));
                });
            } else {
                try {
                    ticketCount = Integer.parseInt(ticketsAvailable);
                } catch (NumberFormatException e) {
                    ticketCount = 0; // Default to zero if parsing fails
                }

                if (ticketCount < 0) {
                    ticketCount = 0; // Default to zero if negative
                    uiHandler.post(() -> {
                        Toast.makeText(DashboardActivity.this, "Invalid 'Tickets Available', event count set to default 0", Toast.LENGTH_SHORT).show();
                        runOnUiThread(() -> etTicketsAvailable.setText(String.valueOf(0)));
                    });
                }
            }

            // Set the eventId
            uiHandler.post(() -> etEventId.setText(eventId));

            // Save it into Database
            Event nEvent = new Event(eventId, eventName, eventCategoryId, ticketCount, eventActive);
            mEventViewModel.addEvent(nEvent);
            // Increase the count by one to corresponding Category
            eventCategory.increaseCount(1);
            mEventCategoryViewModel.updateCategory(eventCategory);

            // Toast the success message
            uiHandler.post(() -> {
                Toast.makeText(DashboardActivity.this, "Event Saved: " + eventId + " to " + eventCategoryId, Toast.LENGTH_SHORT).show();
                callback.onSaveSuccess(eventId);
            });
        });
    }


    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            clearFields();
            tvGesture.setText("onLongPress");
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            save(new SaveEventCallback() {
                @Override
                public void onSaveSuccess(String eventId) {
                    Snackbar snackbar = Snackbar.make(touchPad, "New Event Saved", Snackbar.LENGTH_LONG).setAction("UNDO", v -> {
                        undoAction(eventId);
                        refreshCategories();
                    });
                    snackbar.show();
                }
                @Override
                public void onSaveFailure() {
                }
            });

            tvGesture.setText("onDoubleTap");
            return super.onDoubleTap(e);
        }
    }
}
