package com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson_8_engurazov_kotlin.R
import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note
import com.cloniamix.lesson_8_engurazov_kotlin.utils.MyListener
import kotlinx.android.synthetic.main.view_note_item.view.*

class NoteAdapter(private val listener: MyListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var noteList: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = noteList.size

    fun setData(notes: List<Note>){
        noteList = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(note: Note){
            itemView.setOnClickListener { listener.onItemClick(note) }

            itemView.setOnLongClickListener { listener.onLongItemClick(note)
                true}

            if (note.header == "")itemView.textViewHeader.visibility = View.GONE
            else {
                itemView.textViewHeader.visibility = View.VISIBLE
                itemView.textViewHeader.text = note.header
            }
            itemView.textViewContent.text = note.content
            itemView.cardViewItem.setCardBackgroundColor(Color.parseColor(note.backgroundColor))
        }
    }


}