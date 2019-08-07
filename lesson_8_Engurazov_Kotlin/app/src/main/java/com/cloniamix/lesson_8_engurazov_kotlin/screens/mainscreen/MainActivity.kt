package com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cloniamix.lesson_8_engurazov_kotlin.R
import com.cloniamix.lesson_8_engurazov_kotlin.room.AppDatabase
import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note
import com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen.adapter.NoteAdapter
import com.cloniamix.lesson_8_engurazov_kotlin.screens.notescreen.NoteActivity
import com.cloniamix.lesson_8_engurazov_kotlin.utils.MyListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyListener {

    companion object {

        const val APP_TAG = "Lesson 8"

        private const val FLAG_PROGRESS = 0 // флаг показа прогресса
        private const val FLAG_DATA = 1 // флаг для показа списка заметок
        private const val FLAG_EMPTY = 2 // флаг для показа пустой view, если нет данных

        private const val SPAN_COUNT = 2
    }

    private var disposable: Disposable? = null
    private var disposableSearch: Disposable? = null
    private lateinit var db: AppDatabase
    private val adapter = NoteAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showState(FLAG_PROGRESS)

        toolbarMainActivity.inflateMenu(R.menu.menu_main)
        val menuItem = toolbarMainActivity.menu.findItem(R.id.itemSearch)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                showToast(query.toString())
                searchNote(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNote("%$newText%")
                return false
            }
        })
        searchView.setOnCloseListener { getAllNotesNotArchived()
        false}

        floatingActionButton.setOnClickListener { startActivity(NoteActivity.createStartIntent(this, null)) }

        db = AppDatabase.getInstance(this)

        recyclerViewNoteList.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewNoteList.adapter = adapter
        recyclerViewNoteList.addItemDecoration(ItemOffsetDecoration(resources.getDimension(R.dimen.padding_recycler_view_note_list).toInt()))

        getAllNotesNotArchived()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposableSearch?.dispose()
    }

    // короткое нажатие на элемент списка заметок
    override fun onItemClick(note: Note) {
        startActivity(NoteActivity.createStartIntent(this, note))
    }

    // долгое нажате на элемент списка заметок
    override fun onLongItemClick(note: Note) {
        showDialog(note)
    }


    private fun searchNote(searchText: String) {

        disposableSearch = db.noteDao().getNotesBySearchText(searchText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { notes -> updateUi(notes) },
                { throwable ->
                    Log.d(APP_TAG, throwable.toString())
                    showToast(getString(R.string.db_error_toast_text))
                })
    }


    // получение списка заметок из БД и подписка на изменения в БД
    private fun getAllNotesNotArchived() {
        disposable = db.noteDao().getAllNotesNotArchived(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUi)
            { t ->
                showToast("ошибка ${t.message}")
                showState(FLAG_EMPTY)
            }
    }

    // обновление экрана
    private fun updateUi(notes: List<Note>) {
        if (notes.isEmpty()) showState(FLAG_EMPTY)
        else {
            showState(FLAG_DATA)
            adapter.setData(notes)

        }
    }

    // выбор view для показа в зависимости от флага
    private fun showState(flagState: Int) {
        viewFlipperStates.displayedChild = flagState
    }

    // показывает диалог с выбором действий над заметкой: удалить, в архив
    private fun showDialog(note: Note) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(note.header)
            .setMessage(getString(R.string.main_activity_dialog_message_text))
            .setPositiveButton(getString(R.string.main_activity_dialog_positive_button_text)) { dialog, _ ->
                archiveNote(note)
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.main_dialog_negativ_button_text)) { dialog, _ ->
                deleteNote(note)
                dialog.cancel()
            }
            .show()
    }

    private fun deleteNote(note: Note) {
        disposable = db.noteDao().deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showToast(getString(R.string.note_text) + note.header + getString(R.string.note_delet_text)) }
    }

    private fun archiveNote(note: Note) {

        note.archived = true

        disposable = db.noteDao().updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showToast(getString(R.string.note_text) + { note.header } + getString(R.string.in_archive_text)) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
