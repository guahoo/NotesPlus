package com.guahoo.notesplus

class NotesRepository(private val notesDao: NotesDao) {
    val allNotes = notesDao.getAllNotes()

    suspend fun insert(noteModel: NoteModel){
        notesDao.insert(noteModel)
    }

    suspend fun delete(noteModel: NoteModel){
        notesDao.delete(noteModel)
    }

    suspend fun update(noteModel: NoteModel){
        notesDao.update(noteModel)
    }
}