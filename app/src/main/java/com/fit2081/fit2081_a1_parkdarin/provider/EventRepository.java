package com.fit2081.fit2081_a1_parkdarin.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.fit2081_a1_parkdarin.Event;
import com.fit2081.fit2081_a1_parkdarin.EventCategory;

import java.util.List;

public class EventRepository {
    private EventDAO mEventDAO;
    private LiveData<List<Event>> mAllEvents;
    EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);

        mEventDAO = db.eventDAO();

        mAllEvents = mEventDAO.getAllEvents();

    }

    LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }


    void addEvent(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> mEventDAO.addEvent(event));
    }

    void deleteAllEvents(){
        EventDatabase.databaseWriteExecutor.execute(()->{
            mEventDAO.deleteAllEvents();
        });
    }

    LiveData<Event> getEvent(String eventId) {
        return mEventDAO.getEvent(eventId);
    }

    void deleteEvent(String eventId) {
        EventDatabase.databaseWriteExecutor.execute(() -> mEventDAO.deleteEvent(eventId));
    }

}
