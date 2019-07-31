package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import kotlinx.android.synthetic.main.bridge_list_fragment.*


class BridgesListFragment :
    Fragment(),
    BridgesAdapter.BridgesListItemListener,
    BridgesListMvpView {

    companion object {
        private const val FLAG_PROGRESS = 0
        private const val FLAG_ERROR = 1
        private const val FLAG_DATA = 2

        fun newInstance(): BridgesListFragment {
            return BridgesListFragment()
        }
    }

    private lateinit var listener: OnBridgesListFragmentInteractionListener

    private var bridgesAdapter = ApplicationComponents.getInstance()?.provideBridgesAdapter()

    private val bridgesListFragmentPresenter: BridgesListFragmentPresenter =
        ApplicationComponents.getInstance()!!.provideBridgeListPresenter()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBridgesListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragment2InteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bridge_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        imageViewRefresh.setOnClickListener { bridgesListFragmentPresenter.onRefresh() }
        swipeRefresh.setOnRefreshListener { bridgesListFragmentPresenter.onRefresh() }
        recyclerViewBridgeList.layoutManager = LinearLayoutManager(context)
        recyclerViewBridgeList.adapter = bridgesAdapter
        bridgesListFragmentPresenter.attachView(this)
        bridgesListFragmentPresenter.onCreate()
    }

    override fun showBridges(bridges: List<Bridge>) {
        viewFlipperStates.displayedChild = FLAG_DATA
        swipeRefresh.isRefreshing = false
        bridgesAdapter?.setBridges(bridges, this)

    }

    override fun showErrorState() {
        viewFlipperStates.displayedChild = FLAG_ERROR
    }

    override fun showProgress() {
        viewFlipperStates.displayedChild = FLAG_PROGRESS
    }

    //вызывается в адаптере
    override fun bridgeItemClick(bridge: Bridge) {
        Toast.makeText(activity, bridge.name, Toast.LENGTH_SHORT).show()
    }


    interface OnBridgesListFragmentInteractionListener
    //todo: обработать все клики
}