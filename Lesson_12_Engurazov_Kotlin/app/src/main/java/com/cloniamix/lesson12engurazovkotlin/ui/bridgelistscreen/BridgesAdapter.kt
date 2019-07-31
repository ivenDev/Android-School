package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import android.text.format.DateUtils.HOUR_IN_MILLIS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.Divorce
import com.cloniamix.lesson12engurazovkotlin.ui.MainActivity.Companion.STATUS_LATE
import com.cloniamix.lesson12engurazovkotlin.ui.MainActivity.Companion.STATUS_NORMAL
import com.cloniamix.lesson12engurazovkotlin.ui.MainActivity.Companion.STATUS_SOON
import kotlinx.android.synthetic.main.view_bridge_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BridgesAdapter(/*private val listener: BridgesListFragment.OnBridgesListFragmentInteractionListener*/) :
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

    fun setBridges(bridges: List<Bridge>, listener: BridgesListItemListener){
        this.bridges = bridges
        this.listener = listener
        notifyDataSetChanged()
    }

//    fun setListener(listener: BridgesListItemListener){
//        this.listener = listener
//        notifyDataSetChanged()
//    }


    inner class BridgesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bridge: Bridge) {
            itemView.setOnClickListener { listener?.bridgeItemClick(bridge) }
            itemView.textViewBridgeName.text = bridge.name

            setStatusIconResId(getBridgeStatus(bridge.divorces))
            itemView.textViewTime.text = getStringDivorceTime(bridge.divorces)
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

        private fun getStringDivorceTime(divorceTimesList: List<Divorce>): String {
            val formatter = SimpleDateFormat("h:mm")
            var divorceTime = ""
            for (divorce in divorceTimesList) {
                divorceTime =
                    divorceTime + formatter.format(divorce.start) + " - " + formatter.format(divorce.end) + "  "
            }
            return divorceTime
        }

        private fun getBridgeStatus(divorceTimesList: List<Divorce>): Int {

            var bridgeStatus = STATUS_NORMAL

            val currentTime = Calendar.getInstance()

            for (divorce in divorceTimesList) {

                val startTime: Calendar = Calendar.getInstance()
                startTime.timeInMillis = divorce.start.time
                startTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                val endTime: Calendar = Calendar.getInstance()
                endTime.timeInMillis = divorce.end.time
                endTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                if (bridgeStatus != STATUS_LATE && startTime.timeInMillis - currentTime.timeInMillis in 0..HOUR_IN_MILLIS)
                    bridgeStatus = STATUS_SOON

                if (currentTime.after(startTime) && currentTime.before(endTime))
                    bridgeStatus = STATUS_LATE
            }

            return bridgeStatus
        }
    }

    interface BridgesListItemListener{
        fun bridgeItemClick(bridge: Bridge)
    }

}