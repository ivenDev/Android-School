package com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson_7_engurazov_kotlin.MyListener
import com.cloniamix.lesson_7_engurazov_kotlin.R
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridge
import kotlinx.android.synthetic.main.view_bridge_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BridgesAdapter(private val bridges: ArrayList<Bridge>, private val listener: MyListener) : RecyclerView.Adapter<BridgesAdapter.BridgesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_bridge_item, parent, false)

        return BridgesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bridges.size
    }

    override fun onBindViewHolder(holder: BridgesViewHolder, position: Int) {
        holder.bind(bridges[position])
    }


    inner class BridgesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(bridge: Bridge){
            itemView.setOnClickListener { listener.onMyClick(bridge) }
            itemView.textViewBridgeName.text = bridge.name

            //TODO: выполнить преобразование и проверку времени. и установить нужную иконку
            var time = ""
            for (divorce in bridge.divorces){
                /*val formater: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                val date: Date = formater.parse(divorce.start)

                val start = date.toString()*/
                time = time + divorce.start + "-" + divorce.end + " "
            }
            itemView.textViewTime.text = time
        }
    }
}