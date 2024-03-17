package com.example.personalorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.personalorganizer.database.DBHelper;
import com.example.personalorganizer.database.TaskModel;

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

        DBHelper db_HELPER_GLOBAL_SCOPE = new DBHelper( this);

        saveNoteButton = (Button) findViewById(R.id.addNewNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<TaskModel> tasks = db_HELPER_GLOBAL_SCOPE.getAllTasks();
                int nextNoteId = tasks.size() + 1;

                TaskModel task = new TaskModel(nextNoteId,
                        noteName.getText().toString(),
                        noteDescription.getText().toString());
                db_HELPER_GLOBAL_SCOPE.addTask(task);

                finish();

            }
        });

    }
}


