package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.BridgeHelper
import kotlinx.android.synthetic.main.view_bridge_item.view.*
import java.util.*

class BridgesAdapter :
    RecyclerView.Adapter<BridgesAdapter.BridgesViewHolder>() {

    private var bridges: List<Bridge> = ArrayList()
    private var listener: BridgesListItemListener? = null

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

    fun setBridges(bridges: List<Bridge>, listener: BridgesListItemListener) {
        this.bridges = bridges
        this.listener = listener
        notifyDataSetChanged()
    }

    inner class BridgesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bridge: Bridge) {
            itemView.setOnClickListener { listener?.bridgeItemClick(bridge.id) }
            itemView.textViewBridgeName.text = bridge.name

            itemView.imageViewStatusIcon.setImageResource(BridgeHelper.getStatusIconResId(bridge))

            itemView.textViewTime.text = BridgeHelper.getStringDivorceTime(bridge.divorces)
        }
    }

    interface BridgesListItemListener {
        fun bridgeItemClick(bridgeId: Int)
    }

}