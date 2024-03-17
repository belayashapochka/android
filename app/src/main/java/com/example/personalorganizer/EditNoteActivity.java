package com.example.personalorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.personalorganizer.database.DBHelper;
import com.example.personalorganizer.database.TaskModel;

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

        DBHelper db_HELPER_GLOBAL_SCOPE = new DBHelper( this);

        saveNoteButton = (Button) findViewById(R.id.saveNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currentCursor = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_NOTE_ID));
                Log.d("NODEINDEX", String.format("%s",String.valueOf(noteIndex)));



                TaskModel task = db_HELPER_GLOBAL_SCOPE.getTaskById(currentCursor);

                task.setName(noteName.getText().toString());
                task.setDescription(noteDescription.getText().toString());

                db_HELPER_GLOBAL_SCOPE.updateTask(task);


                finish();

            }
        });

    }
}
