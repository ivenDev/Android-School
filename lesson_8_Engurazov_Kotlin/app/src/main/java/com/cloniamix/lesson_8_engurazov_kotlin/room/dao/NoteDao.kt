package com.cloniamix.lesson_8_engurazov_kotlin.room.dao

import androidx.room.*
import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flowable<List<Note>>

    @Query("SELECT * FROM note WHERE archived = :archived")
    fun getAllNotesNotArchived(archived: Boolean = false): Flowable<List<Note>>

    @Query("SELECT * FROM note WHERE header LIKE :searchText OR content LIKE :searchText")
    fun getNotesBySearchText(searchText: String): Single<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: List<Note>): Completable


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note): Completable

    @Delete
    fun deleteNote(note: Note): Completable

    @Update
    fun updateNote(note: Note): Completable

}