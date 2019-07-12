package com.cloniamix.lesson_8_engurazov_kotlin.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Note (
    @ColumnInfo(name = "header") var header: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "archived") var archived: Boolean = false,
    @ColumnInfo(name = "background") var backgroundColor: String = "#ffffff",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Int = 0) : Parcelable

