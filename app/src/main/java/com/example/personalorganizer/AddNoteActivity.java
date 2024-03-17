package com.example.personalorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.personalorganizer.database.DBHelperV2;
import com.example.personalorganizer.database.TaskDao;
import com.example.personalorganizer.database.TaskModelV2;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private int noteIndex;

    private Button saveNoteButton;

    private EditText noteName, noteDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        noteName = (EditText) findViewById(R.id.noteName);
        noteDescription = (EditText) findViewById(R.id.noteDescription);

        Intent intent = getIntent();
        noteName.setText("Первая задача");
        noteDescription.setText("Описание");


        DBHelperV2 db = Room.databaseBuilder(getApplicationContext(),
                DBHelperV2.class, "tasks.db").build();
        TaskDao taskDao = db.taskDao();
        saveNoteButton = (Button) findViewById(R.id.addNewNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                List<TaskModelV2> tasks = taskDao.getAllTasks();
                int nextNoteId = tasks.size() + 1;

                TaskModelV2 task = new TaskModelV2(nextNoteId,
                        noteName.getText().toString(),
                        noteDescription.getText().toString());
                taskDao.addTask(task);

                finish();

            }
        });

    }
}


