package com.guahoo.notesplus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val context: Context,
                  val noteClickInterface: NoteClickInterface,
                  val noteClickDeleteInterFace: NoteClickDeleteInterFace
): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val allNotes = ArrayList<NoteModel>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteTitleTV: TextView = itemView.findViewById(R.id.idTvNoteTitle)
        val noteDateStampTV: TextView = itemView.findViewById(R.id.idNoteTimeStamp)
        val noteButtonDelete: ImageView = itemView.findViewById(R.id.idIVDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTitleTV.text = allNotes[position].noteTitle
        holder.noteDateStampTV.text = buildString {
            append("Обновлено: ")
            append(allNotes[position].timestamp)
        }

        holder.noteButtonDelete.setOnClickListener {
            noteClickDeleteInterFace.onClickDelete(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }

    }

    override fun getItemCount(): Int{
        return allNotes.size
    }

    fun updateNotesList(newList: List<NoteModel>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterFace{
    fun onClickDelete(noteModel: NoteModel)
}

interface NoteClickInterface{
    fun onNoteClick(noteModel: NoteModel)
}