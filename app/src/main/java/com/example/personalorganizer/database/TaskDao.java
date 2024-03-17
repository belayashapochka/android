package com.example.personalorganizer.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void addTask(TaskModelV2 task);

    @Update
    void updateTask(TaskModelV2 task);

    @Query("SELECT * FROM tasks")
    List<TaskModelV2> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskModelV2 getTaskById(int id);


}