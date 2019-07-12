package com.cloniamix.lesson_8_engurazov_kotlin.screens.mainscreen

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = offset
        outRect.left = offset
        outRect.top = offset
        outRect.bottom = offset
    }
}