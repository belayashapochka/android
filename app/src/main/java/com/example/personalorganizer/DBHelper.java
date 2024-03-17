package com.example.personalorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY, name TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        Log.d("DATABASE", "Dropped data in task");
        onCreate(db);
    }

    public void addTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", task.getName());
        values.put("description", task.getDescription());
        db.insert("tasks", null, values);
        db.close();
    }
    public void updateTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", task.getName());
        values.put("description", task.getDescription());
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(task.getId())};
        db.update("tasks", values, whereClause, whereArgs);
        db.close();
    }

    public List<TaskModel> getAllTasks() {
        List<TaskModel> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM tasks";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();
                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public TaskModel getTaskById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tasks", new String[]{"id", "name", "description"},
                "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        TaskModel task = new TaskModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return task;
    }
}