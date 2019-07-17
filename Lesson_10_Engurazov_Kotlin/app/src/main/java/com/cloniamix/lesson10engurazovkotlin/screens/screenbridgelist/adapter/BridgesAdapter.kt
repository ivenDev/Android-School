package com.cloniamix.lesson10engurazovkotlin.screens.screenbridgelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson10engurazovkotlin.R
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridge
import com.cloniamix.lesson10engurazovkotlin.utils.MyListener
import com.cloniamix.lesson10engurazovkotlin.utils.Utils
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_LATE
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_NORMAL
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_SOON
import kotlinx.android.synthetic.main.view_bridge_item.view.*

class BridgesAdapter(private val bridges: ArrayList<Bridge>, private val listener: MyListener) :
    RecyclerView.Adapter<BridgesAdapter.BridgesViewHolder>() {

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


    inner class BridgesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bridge: Bridge) {
            itemView.setOnClickListener { listener.onMyClick(bridge) }
            itemView.textViewBridgeName.text = bridge.name

            setStatusIconResId(Utils.getBridgeStatus(bridge.divorces))
            itemView.textViewTime.text = Utils.getStringDivorceTime(bridge.divorces)
        }

        private fun setStatusIconResId(status: Int) {
            when (status) {
                STATUS_SOON -> {
                    itemView.imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_soon)
                }
                STATUS_LATE -> {
                    itemView.imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_late)
                }
                STATUS_NORMAL -> {
                    itemView.imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_normal)
                }
            }
        }
    }
}