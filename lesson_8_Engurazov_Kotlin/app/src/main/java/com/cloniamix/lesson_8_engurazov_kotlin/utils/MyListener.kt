package com.cloniamix.lesson_8_engurazov_kotlin.utils

import com.cloniamix.lesson_8_engurazov_kotlin.room.entity.Note

interface MyListener {

    fun onItemClick(note: Note)
    fun onLongItemClick(note: Note)
}