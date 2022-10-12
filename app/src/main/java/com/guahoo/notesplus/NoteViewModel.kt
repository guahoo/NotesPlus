package com.guahoo.notesplus

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
     val allNotes: LiveData<List<NoteModel>>
    private val repository: NotesRepository

    init {
        val dao = NoteDataBase.getDataBase(application).getNotesDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
        Log.v("Eхехе","охохо")
    }

    fun deleteNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(noteModel)
    }

    fun insertNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(noteModel)
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(noteModel)
    }
}