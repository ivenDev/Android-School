package com.cloniamix.lesson_8_engurazov_kotlin.screens.notescreen

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cloniamix.lesson_8_engurazov_kotlin.R
import com.cloniamix.lesson_8_engurazov_kotlin.room.AppDatabase
import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note
import com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen.ItemOffsetDecoration
import com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen.MainActivity
import com.cloniamix.lesson_8_engurazov_kotlin.screens.notescreen.adapter.ColorAdapter
import com.cloniamix.lesson_8_engurazov_kotlin.utils.ColorListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.view_color_recycler.view.*

class NoteActivity : AppCompatActivity(), ColorListener {

    companion object {
        private const val TAG_NOTE = "noteTag"
        private const val SPAN_COUNT = 4

        fun createStartIntent(context: Context, note: Note?): Intent {
            val intent = Intent(context, NoteActivity::class.java)

            if (note != null) intent.putExtra(TAG_NOTE, note)

            return intent
        }
    }

    private var disposable: Disposable? = null
    private lateinit var db: AppDatabase
    private lateinit var note: Note

    private lateinit var noteColor: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)


        if (intent.hasExtra(TAG_NOTE)) {
            note = intent.getParcelableExtra(TAG_NOTE)
            editTextHeader.setText(note.header)
            editTextNoteText.setText(note.content)
            noteColor = note.backgroundColor
            constraintLayoutNote.setBackgroundColor(Color.parseColor(noteColor))
        } else note = Note()

        db = AppDatabase.getInstance(this)

        toolbarNoteActivity.setNavigationOnClickListener { onBackPressed() }
        toolbarNoteActivity.inflateMenu(R.menu.menu_note_activity)
        toolbarNoteActivity.setOnMenuItemClickListener {
            if(it.itemId == R.id.itemColor) showDialog()

            true
        }
    }


    private fun showDialog(){
        val colorRecyclerView = layoutInflater.inflate(R.layout.view_color_recycler, null)
        colorRecyclerView.recyclerViewColorList.layoutManager =
            GridLayoutManager(this, SPAN_COUNT)

        colorRecyclerView.recyclerViewColorList.addItemDecoration(ItemOffsetDecoration(resources.getDimension(R.dimen.padding_recycler_view_note_list).toInt()))


        val colorAdapter = ColorAdapter(this)
        colorAdapter.setData(this.resources.getStringArray(R.array.colorList).toList(), note.backgroundColor)
        colorRecyclerView.recyclerViewColorList.adapter =colorAdapter

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.color_dialog_title_text))
            .setView(colorRecyclerView)
            .setPositiveButton(getString(R.string.dialog_button_ok_text)) { dialog, _ ->  setNoteBackgroundColor(dialog) }
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        note.header = getHeader()
        note.content = getNoteText()
        insertNoteIntoDb()
    }

    private fun setNoteBackgroundColor(dialog: DialogInterface){
        constraintLayoutNote.setBackgroundColor(Color.parseColor(noteColor))
        note.backgroundColor = noteColor
        dialog.dismiss()
    }
    private fun insertNoteIntoDb(){

        disposable = db.noteDao().insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ showToast(getString(R.string.toast_save_text)) },
                { throwable ->
                    Log.d(MainActivity.APP_TAG, throwable.toString())
                    showToast(getString(R.string.db_error_toast_text)) })
    }

    private fun getHeader(): String{
        return editTextHeader.text.toString()
    }

    private fun getNoteText(): String{
        return editTextNoteText.text.toString()
    }

    override fun changeColor(color: String) {
//        constraintLayoutNote.setBackgroundColor(Color.parseColor(color))
//        note.backgroundColor = color
        noteColor = color

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
