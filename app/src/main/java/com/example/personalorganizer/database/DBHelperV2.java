package com.example.personalorganizer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class DBHelperV2 extends RoomDatabase {
    public abstract TaskDao taskDao();
}
