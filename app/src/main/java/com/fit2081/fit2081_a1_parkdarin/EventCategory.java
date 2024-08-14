package com.fit2081.fit2081_a1_parkdarin;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class EventCategory {
    public static final String TABLE_NAME = "categories";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @ColumnInfo(name = "categoryName")
    private String categoryName;
    @ColumnInfo(name = "categoryCount")
    private int count;
    @ColumnInfo(name = "categoryIsActive")
    private boolean isActive;
    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    public EventCategory(String categoryId, String categoryName, int count, boolean isActive, String eventLocation) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        if(count>=1){
            this.count = count;
        }else {
            this.count=0;
        }
        this.isActive = isActive;
        this.eventLocation = eventLocation;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCount() {
        return count;
    }

    public boolean isActive() {
        return isActive;
    }
    public String getEventLocation() {
        return this.eventLocation;
    }

    public void increaseCount(int val){
        this.count += val;
    }
}
