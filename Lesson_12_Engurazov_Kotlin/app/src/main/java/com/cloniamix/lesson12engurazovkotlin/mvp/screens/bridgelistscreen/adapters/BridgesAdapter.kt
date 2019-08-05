package com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgelistscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter.Companion.STATUS_LATE
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter.Companion.STATUS_NORMAL
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter.Companion.STATUS_SOON
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

            setStatusIconResId(BasePresenter.getBridgeStatus(bridge.divorces))
            itemView.textViewTime.text = BasePresenter.getStringDivorceTime(bridge.divorces)
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

    interface BridgesListItemListener {
        fun bridgeItemClick(bridgeId: Int)
    }

}