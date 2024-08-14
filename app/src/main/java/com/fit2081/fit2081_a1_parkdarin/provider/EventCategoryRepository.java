package com.fit2081.fit2081_a1_parkdarin.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.fit2081_a1_parkdarin.Event;
import com.fit2081.fit2081_a1_parkdarin.EventCategory;

import java.util.List;

public class EventCategoryRepository {
    private EventDAO mEventDAO;
    private LiveData<List<EventCategory>> mAllCategories;
    private LiveData<List<Event>> mAllEvents;

    EventCategoryRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);

        mEventDAO = db.eventDAO();

        mAllCategories = mEventDAO.getAllCategories();
    }

    LiveData<List<EventCategory>> getAllCategories() {
        return mAllCategories;
    }
    List<EventCategory> getCategorybyId(String categoryId){ return mEventDAO.getCategorybyId(categoryId);
    }

    void addCategory(EventCategory category) {
        EventDatabase.databaseWriteExecutor.execute(() -> mEventDAO.addCategory(category));
    }

    void deleteAllCategories(){
        EventDatabase.databaseWriteExecutor.execute(()->{
            mEventDAO.deleteAllCategories();
        });
    }

    public void updateCategory(EventCategory category) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            mEventDAO.updateCategory(category);
        });
    }
}
