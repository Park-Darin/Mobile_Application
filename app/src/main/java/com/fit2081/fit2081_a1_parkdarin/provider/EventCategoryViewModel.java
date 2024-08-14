package com.fit2081.fit2081_a1_parkdarin.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.fit2081_a1_parkdarin.EventCategory;

import java.util.List;

public class EventCategoryViewModel extends AndroidViewModel {
    private EventCategoryRepository mRepository;
    private LiveData<List<EventCategory>> mAllCategories;

    public EventCategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EventCategoryRepository(application);
        mAllCategories= mRepository.getAllCategories();
    }

    public LiveData<List<EventCategory>> getAllCategories() {
        return mAllCategories;
    }
    public List<EventCategory> getCategorybyId(String categoryId) { return mRepository.getCategorybyId(categoryId);}

    public void addCategory(EventCategory category) {
        mRepository.addCategory(category);
    }
    public void deleteAllCategories(){
        mRepository.deleteAllCategories();
    }

    public void updateCategory(EventCategory category) {
        mRepository.updateCategory(category);
    }

}
