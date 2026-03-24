package com.example.studentsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float grade;

    public Student(String name, float grade) {
        this.name = name;
        this.grade = grade;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getGrade() { return grade; }
    public void setGrade(float grade) { this.grade = grade; }
}
