package com.example.personalorganizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public final static String EXTRA_NOTE_ID = "com.example.personalorganizer.NOTE_ID";
    public final static String EXTRA_NOTE_NAME = "com.example.personalorganizer.NOTE_NAME";
    public final static String EXTRA_NOTE_DESCRIPTION = "com.example.personalorganizer.NOTE_DESCRIPTION";
    private TextView noteName, noteDescription;
    private Button addNoteButton, editNoteButton, showNoteButton;
    private ImageButton prevNote, nextNote;
    private int noteIndex = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ONCHANGE", "CHANGE FOCUS");
        Log.d("NODEINDEX", String.format("%s",String.valueOf(noteIndex)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteName = (TextView) findViewById(R.id.noteName);
        noteDescription = (TextView) findViewById(R.id.noteDescription);
        addNoteButton = (Button) findViewById(R.id.addNoteButton);

        DBHelper db_HELPER_GLOBAL_SCOPE = new DBHelper( this);
        List<TaskModel> tasks = db_HELPER_GLOBAL_SCOPE.getAllTasks();
        int maxNoteId = tasks.size();
        if( maxNoteId==0){
            noteName.setText("Создай первую заметку");
            noteDescription.setText("Не бойся");
        } else
        // TODO Надо подумать !
        {
            noteName.setText(tasks.get(0).getName().toString());
            noteDescription.setText(tasks.get(0).getDescription().toString());
        }
        editNoteButton = (Button) findViewById(R.id.editNoteButton);
        showNoteButton = (Button) findViewById(R.id.showNoteButton);
        showNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<TaskModel> tasks = db_HELPER_GLOBAL_SCOPE.getAllTasks();
                int maxNoteId = tasks.size();
                noteIndex = maxNoteId;

                TaskModel task = db_HELPER_GLOBAL_SCOPE.getTaskById(noteIndex);
                noteName.setText(task.getName());
                noteDescription.setText(task.getDescription());
            }
        });
        prevNote = (ImageButton) findViewById(R.id.prevNoteImageButton);
        prevNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("NODEINDEX", String.format("%s in Prev button",String.valueOf(noteIndex)));
                if(noteIndex == 0){
//                    Toast.makeText(MainActivity.this, "No next note available", Toast.LENGTH_SHORT).show();
                    showNoDataPopup(
                            "И что там? в пустоте",
                            "Ты не выйдешь за массив, не бойся) ");
                }else {
                    noteIndex = (noteIndex <= 2) ? 1 : noteIndex - 1;

                    TaskModel task = db_HELPER_GLOBAL_SCOPE.getTaskById(noteIndex);
                    noteName.setText(task.getName());
                    noteDescription.setText(task.getDescription());
                }
            }
        });

        nextNote = (ImageButton) findViewById(R.id.nextNoteImageButton);
        nextNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<TaskModel> tasks = db_HELPER_GLOBAL_SCOPE.getAllTasks();
                int maxCurrentNote = tasks.size();

                if( maxCurrentNote==0){
                    showNoDataPopup(
                            "Куда прём? ",
                            "Давай эт самое), создавай уже заметку");
                }else {
                    noteIndex = (noteIndex == maxCurrentNote) ? maxCurrentNote : noteIndex + 1;

                    TaskModel task = db_HELPER_GLOBAL_SCOPE.getTaskById(noteIndex);
                    noteName.setText(task.getName());
                    noteDescription.setText(task.getDescription());
                }

            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("noteIndex", noteIndex);
        Log.d("ONSAVEINTANCESTATE", "SAVED NOTE INDEX");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        noteIndex = savedInstanceState.getInt("noteIndex");
        Log.d("ONSAVEINTANCESTATE", "SAVED NOTE INDEX");
    }

    /** Нериальный майндгейм с жизненным циклом :) */
    @Override
    protected void onResume() {
        super.onResume();
        if (noteIndex != 0) {
            updateUI(noteIndex);
        }
    }

    private void updateUI(int currentCursor) {
        DBHelper db_HELPER_GLOBAL_SCOPE = new DBHelper( this);
        TaskModel task = db_HELPER_GLOBAL_SCOPE.getTaskById(currentCursor);
            noteName.setText(task.getName());
            noteDescription.setText(task.getDescription());
    }

    /** Called when the user clicks the Редактировать :)  button */
    public void editNote(View view) {
        int currentCursor = noteIndex;

        if(currentCursor == 0){
            DBHelper db_HELPER_GLOBAL_SCOPE = new DBHelper( this);
            List<TaskModel> tasks = db_HELPER_GLOBAL_SCOPE.getAllTasks();
            if (tasks.size() == 0){
                noteName.setText("Первая задача");
                noteDescription.setText("Описание");
                TaskModel task = new TaskModel(currentCursor,
                        noteName.getText().toString(),
                        noteDescription.getText().toString());
                db_HELPER_GLOBAL_SCOPE.addTask(task);
            }
        }
        Intent intent = new Intent(this, EditNoteActivity.class);
        String noteName = ((TextView) findViewById(R.id.noteName)).getText().toString();
        String noteDescription = ((TextView) findViewById(R.id.noteDescription)).getText().toString();

        intent.putExtra(EXTRA_NOTE_ID,  String.valueOf(currentCursor));
        intent.putExtra(EXTRA_NOTE_NAME, noteName);
        intent.putExtra(EXTRA_NOTE_DESCRIPTION, noteDescription);
        startActivity(intent);
    }

    public void saveNote(View view)
    {
        Intent intent = new Intent(this, AddNoteActivity.class);

        intent.putExtra(EXTRA_NOTE_ID,  String.valueOf(noteIndex));
        noteIndex += 1;
        startActivity(intent);

    }
    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    private void showNoDataPopup(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK? по хорошему)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}