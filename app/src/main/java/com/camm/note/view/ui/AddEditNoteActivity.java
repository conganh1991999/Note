package com.camm.note.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.camm.note.R;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText edtNewTitle;
    private EditText edtNewDescription;
    private NumberPicker pickerNewPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edtNewTitle = findViewById(R.id.edtNewTitle);
        edtNewDescription = findViewById(R.id.edtNewDescription);
        pickerNewPriority = findViewById(R.id.pickerNewPriority);

        pickerNewPriority.setMinValue(1);
        pickerNewPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            setTitle("Edit Note");
            edtNewTitle.setText(intent.getStringExtra("title"));
            edtNewDescription.setText(intent.getStringExtra("description"));
            pickerNewPriority.setValue(intent.getIntExtra("id", 1));
        }
        else{
            setTitle("Add Note");
        }
    }

    private void saveNote(){
        String title = edtNewTitle.getText().toString();
        String description = edtNewDescription.getText().toString();
        int priority = pickerNewPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please type title and description", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putExtra("priority", priority);

            int id = getIntent().getIntExtra("id", -1);
            if(id != -1){
                intent.putExtra("id", id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
