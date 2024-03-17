package com.example.personalorganizer.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskModelV2 {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;

    public TaskModelV2() {
    }
    public TaskModelV2(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}