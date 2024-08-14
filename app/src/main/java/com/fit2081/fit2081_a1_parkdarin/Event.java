package com.fit2081.fit2081_a1_parkdarin;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {
    public static final String TABLE_NAME = "events";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "eventId")
    private String eventId;
    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "eventCategoryId")
    private String categoryId;
    @ColumnInfo(name = "eventTicketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "eventIsActive")
    private boolean isActive;

    public Event(String eventId, String eventName, String categoryId, int ticketsAvailable, boolean isActive) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.categoryId = categoryId;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }
}
