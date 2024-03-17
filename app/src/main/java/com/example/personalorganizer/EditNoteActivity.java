package com.example.personalorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.personalorganizer.database.DBHelperV2;
import com.example.personalorganizer.database.TaskDao;
import com.example.personalorganizer.database.TaskModelV2;


public class EditNoteActivity extends AppCompatActivity {

    private int noteIndex;

    private Button saveNoteButton;

    private EditText noteName, noteDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteName = (EditText) findViewById(R.id.noteName);
        noteDescription = (EditText) findViewById(R.id.noteDescription);

        Intent intent = getIntent();
        noteName.setText(intent.getStringExtra(MainActivity.EXTRA_NOTE_NAME));
        noteDescription.setText(intent.getStringExtra(MainActivity.EXTRA_NOTE_DESCRIPTION));

        DBHelperV2 db = Room.databaseBuilder(getApplicationContext(),
                DBHelperV2.class, "tasks.db").build();
        TaskDao taskDao = db.taskDao();

        saveNoteButton = (Button) findViewById(R.id.saveNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currentCursor = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_NOTE_ID));
                Log.d("NODEINDEX", String.format("%s",String.valueOf(noteIndex)));



                TaskModelV2 task = taskDao.getTaskById(currentCursor);

                task.setName(noteName.getText().toString());
                task.setDescription(noteDescription.getText().toString());

                taskDao.updateTask(task);


                finish();

            }
        });

    }
}
