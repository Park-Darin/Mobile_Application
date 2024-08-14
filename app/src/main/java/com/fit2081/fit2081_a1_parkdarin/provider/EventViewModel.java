package com.fit2081.fit2081_a1_parkdarin.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.fit2081_a1_parkdarin.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository mRepository;
    private LiveData<List<Event>> mAllEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllEvents= mRepository.getAllEvents();
    }
    public LiveData<List<Event>> getAllCategories() {
        return mAllEvents;
    }
    public void addEvent(Event event) {
        mRepository.addEvent(event);
    }
    public void deleteAllEvents(){
        mRepository.deleteAllEvents();
    }
    public LiveData<Event> getEvent(String eventId){
        return mRepository.getEvent(eventId);
    }
    public void deleteEvent(String eventId) {
        mRepository.deleteEvent(eventId);
    }

}
