package com.example.studentsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM students ORDER BY name ASC")
    LiveData<List<Student>> getAllStudents();
}
