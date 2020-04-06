package com.camm.note.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.camm.note.data.room.entity.Note;
import com.camm.note.viewmodel.NoteViewModel;
import com.camm.note.R;
import com.camm.note.view.adapter.NoteAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerNotes = findViewById(R.id.listNotes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotes.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerNotes.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
