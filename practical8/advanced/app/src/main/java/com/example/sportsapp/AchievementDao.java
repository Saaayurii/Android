package com.example.sportsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface AchievementDao {
    @Insert
    void insert(Achievement achievement);

    @Delete
    void delete(Achievement achievement);

    @Query("SELECT * FROM achievements ORDER BY trainingDate DESC")
    LiveData<List<Achievement>> getAllSortedByDate();

    @Query("SELECT * FROM achievements WHERE sportType = :sport ORDER BY trainingDate DESC")
    LiveData<List<Achievement>> getBySport(String sport);

    @Query("SELECT DISTINCT sportType FROM achievements ORDER BY sportType ASC")
    LiveData<List<String>> getAllSportTypes();
}
