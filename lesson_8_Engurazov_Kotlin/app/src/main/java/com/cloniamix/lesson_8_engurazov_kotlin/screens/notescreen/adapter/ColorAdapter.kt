package com.cloniamix.lesson_8_engurazov_kotlin.screens.notescreen.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson_8_engurazov_kotlin.R
import com.cloniamix.lesson_8_engurazov_kotlin.utils.ColorListener
import kotlinx.android.synthetic.main.view_color_item.view.*

class ColorAdapter(private val listener: ColorListener) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private val whiteColor = "#ffffff"
    private var colorList: List<String> = ArrayList()
    private var checkPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun getItemCount() = colorList.size


    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {

        holder.bind(colorList[position], position)
    }

    fun setData(colors: List<String>, noteColor: String){
        colorList = colors
        checkPosition = colorList.indexOf(noteColor)
        notifyDataSetChanged()
    }


    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        fun bind(color: String, position: Int){

            if (position == checkPosition) {
                if (color == whiteColor) itemView.imageButtonColor.setImageResource(R.drawable.ic_check_black)
                else itemView.imageButtonColor.setImageResource(R.drawable.ic_check_white)
            }
            else itemView.imageButtonColor.setImageResource(android.R.color.transparent)

            itemView.setOnClickListener {
                listener.changeColor(color)
                checkPosition = position
                itemView.imageButtonColor.setImageResource(R.drawable.ic_check_white)
                notifyDataSetChanged()
            }

            when (val background = itemView.imageButtonColor.background) {
                is GradientDrawable -> {
                    // cast to 'GradientDrawable'
                    background.setColor(Color.parseColor(color))

                }
            }
        }
    }
}