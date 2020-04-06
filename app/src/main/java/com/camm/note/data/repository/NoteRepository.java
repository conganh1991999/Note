package com.camm.note.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.camm.note.data.room.NoteRoomDatabase;
import com.camm.note.data.room.entity.Note;
import com.camm.note.data.room.dao.NoteDao;

import java.util.List;

/* In the most common example, the Repository implements the logic for deciding whether
   to fetch data from a network or use results cached in a local database. */

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public NoteRepository(Application application){
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    // Room executes all queries on a separate thread.

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    public void insert(Note note){
        // new InsertNoteAsyncTask(noteDao).execute(note);
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });
    }

    public void update(Note note){
        // new UpdateNoteAsyncTask(noteDao).execute(note);
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.update(note);
        });
    }

    public void delete(Note note){
        // new DeleteNoteAsyncTask(noteDao).execute(note);
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }

    public void deleteAllNotes(){
        // new DeleteAllNotesAsyncTask(noteDao).execute();
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.deleteAllNotes();
        });
    }

//    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
//        private NoteDao noteDao;
//
//        private InsertNoteAsyncTask(NoteDao noteDao){
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.insert(notes[0]);
//            return null;
//        }
//    }
//
//    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
//        private NoteDao noteDao;
//
//        private UpdateNoteAsyncTask(NoteDao noteDao){
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.update(notes[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
//        private NoteDao noteDao;
//
//        private DeleteNoteAsyncTask(NoteDao noteDao){
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.delete(notes[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
//        private NoteDao noteDao;
//
//        private DeleteAllNotesAsyncTask(NoteDao noteDao){
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            noteDao.deleteAllNotes();
//            return null;
//        }
//    }

}
