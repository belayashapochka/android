package com.example.personalorganizer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskModelV2.class}, version = 1)
public abstract class DBHelperV2 extends RoomDatabase {
    public abstract TaskDao taskDao();
}
