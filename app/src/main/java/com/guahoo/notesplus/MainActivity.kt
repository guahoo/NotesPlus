package com.guahoo.notesplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterFace {
    lateinit var notesRV: RecyclerView
    lateinit var notesInsertButton: FloatingActionButton
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(
            this@MainActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]


        notesRV = findViewById(R.id.idRvNotes)
        notesInsertButton = findViewById(R.id.idInsertButton)
        noteAdapter = NoteAdapter(
            context = this,
            noteClickDeleteInterFace = this@MainActivity,
            noteClickInterface = this@MainActivity
        )
        notesRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }



        viewModel.allNotes.observe(this) { notesModel ->
            notesModel?.let {
                noteAdapter.updateNotesList(it)
            }
        }

        notesInsertButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this@MainActivity.finish()
        }
    }

    override fun onClickDelete(noteModel: NoteModel) {
        viewModel.deleteNote(noteModel)
    }

    override fun onNoteClick(noteModel: NoteModel) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", noteModel.noteTitle)
        intent.putExtra("noteDescription", noteModel.noteDescription)
        intent.putExtra("noteId", noteModel.id)
        startActivity(intent)
        this@MainActivity.finish()
    }
}