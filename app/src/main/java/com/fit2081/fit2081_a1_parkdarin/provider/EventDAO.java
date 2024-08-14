package com.fit2081.fit2081_a1_parkdarin.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fit2081.fit2081_a1_parkdarin.Event;
import com.fit2081.fit2081_a1_parkdarin.EventCategory;

import java.util.List;

@Dao
public interface EventDAO {

    @Query("select * from categories")
    LiveData<List<EventCategory>> getAllCategories();

    @Query("select * from categories where categoryId=:categoryId")
    List<EventCategory> getCategorybyId(String categoryId);

    @Insert
    void addCategory(EventCategory category);

    @Query("delete FROM categories")
    void deleteAllCategories();

    @Update
    void updateCategory(EventCategory category);

    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvent(Event event);

    @Query("delete FROM events")
    void deleteAllEvents();

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    LiveData<Event> getEvent(String eventId);

    @Query("delete from events where eventId= :eventId")
    void deleteEvent(String eventId);


}
