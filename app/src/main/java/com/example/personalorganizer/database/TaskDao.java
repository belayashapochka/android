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
    void addTask(TaskModel task);

    @Update
    void updateTask(TaskModel task);

    @Query("SELECT * FROM tasks")
    List<TaskModel> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskModel getTaskById(int id);


}