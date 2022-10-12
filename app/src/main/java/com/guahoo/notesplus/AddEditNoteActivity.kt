package com.guahoo.notesplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var tvNoteTitle: EditText
    private lateinit var tvNoteDescription: EditText
    private lateinit var btnUpdateNote: Button
    lateinit var noteViewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        tvNoteTitle = findViewById(R.id.idNoteEditText)
        tvNoteDescription = findViewById(R.id.noteDescriptionEt)
        btnUpdateNote = findViewById(R.id.idBtnUpdate)
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")

        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            btnUpdateNote.text = "Обновить запись"
            tvNoteTitle.setText(noteTitle)
            tvNoteDescription.setText(noteDescription)

        } else {
            btnUpdateNote.text = "Сохранить запись"
        }

        btnUpdateNote.setOnClickListener {
            val noteTitle = tvNoteTitle.text.toString()
            val noteDescription = tvNoteDescription.text.toString()

            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                    val currentDate = sdf.format(Date())
                    val updateNote = NoteModel(
                        noteTitle = noteTitle,
                        noteDescription = noteDescription,
                        timestamp = currentDate,
                    )
                    updateNote.id = noteId
                    noteViewModel.updateNote(updateNote)
                    Toast.makeText(this@AddEditNoteActivity, "Updated", Toast.LENGTH_SHORT).show()
                }
            } else {
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                val currentDate = sdf.format(Date())
                val newNote = NoteModel(
                    noteTitle = noteTitle,
                    noteDescription = noteDescription,
                    timestamp = currentDate,
                )
                noteViewModel.insertNote(newNote)
                Toast.makeText(this@AddEditNoteActivity, "Created", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this@AddEditNoteActivity.finish()
        }
    }
}