package com.example.sportsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements")
public class Achievement {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String sportType;
    private String trainingDate;
    private String description;

    public Achievement(String title, String sportType, String trainingDate, String description) {
        this.title = title;
        this.sportType = sportType;
        this.trainingDate = trainingDate;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }
    public String getTrainingDate() { return trainingDate; }
    public void setTrainingDate(String trainingDate) { this.trainingDate = trainingDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
