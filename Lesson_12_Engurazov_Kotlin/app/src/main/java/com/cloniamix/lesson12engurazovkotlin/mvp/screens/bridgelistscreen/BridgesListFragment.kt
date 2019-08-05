package com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgelistscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.BridgesListMvpView
import com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgelistscreen.adapters.BridgesAdapter
import kotlinx.android.synthetic.main.bridge_list_fragment.*
import kotlinx.android.synthetic.main.error_view.*

class BridgesListFragment :
    Fragment(),
    BridgesAdapter.BridgesListItemListener,
    BridgesListMvpView {

    companion object {

        fun newInstance(): BridgesListFragment {
            return BridgesListFragment()
        }
    }

    private var listener: OnBridgesListFragmentInteractionListener? = null

    private var bridgesAdapter = ApplicationComponents.getInstance()?.provideBridgesAdapter()

    private val bridgesListFragmentPresenter: BridgesListFragmentPresenter =
        ApplicationComponents.getInstance()!!.provideBridgesListPresenter()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBridgesListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBridgesListFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bridge_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        textViewError.setOnClickListener { bridgesListFragmentPresenter.onRefresh() }
        swipeRefresh.setOnRefreshListener { bridgesListFragmentPresenter.onSwipeRefresh() }

        toolbar.inflateMenu(R.menu.map_menu)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemMap) {
                listener?.menuMapItemClicked()
            }
            true
        }

        recyclerViewBridgeList.layoutManager = LinearLayoutManager(context)
        recyclerViewBridgeList.adapter = bridgesAdapter
        bridgesListFragmentPresenter.attachView(this)
        bridgesListFragmentPresenter.onViewCreated()
    }

    override fun onDetach() {
        listener = null
        bridgesListFragmentPresenter.detachView()
        super.onDetach()
    }

    override fun showState(stateFlag: Int) {
        viewFlipperStates.displayedChild = stateFlag
    }

    override fun showBridges(bridges: List<Bridge>) {
        swipeRefresh.isRefreshing = false
        bridgesAdapter?.setBridges(bridges, this)
    }

    //region BridgesAdapter callback
    override fun bridgeItemClick(bridgeId: Int) {
        listener?.bridgeItemClicked(bridgeId)
    }
    //endregion

    interface OnBridgesListFragmentInteractionListener {
        fun bridgeItemClicked(bridgeId: Int)
        fun menuMapItemClicked()
    }
}