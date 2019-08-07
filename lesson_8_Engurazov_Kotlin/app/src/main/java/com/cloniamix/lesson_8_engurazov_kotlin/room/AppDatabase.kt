package com.cloniamix.lesson_8_engurazov_kotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cloniamix.lesson_8_engurazov_kotlin.room.dao.NoteDao
import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!::INSTANCE.isInitialized){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "note_list.db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}