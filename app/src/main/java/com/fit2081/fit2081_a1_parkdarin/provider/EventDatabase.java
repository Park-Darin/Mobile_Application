package com.fit2081.fit2081_a1_parkdarin.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.fit2081_a1_parkdarin.EventCategory;
import com.fit2081.fit2081_a1_parkdarin.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EventCategory.class, Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    public static final String EVENT_DATABASE = "event_database";

    public abstract EventDAO eventDAO();
    private static volatile EventDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Since this class is an absract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */
    static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                EventDatabase.class, EVENT_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
