package com.cloniamix.lesson12engurazovkotlin.ui

import android.os.Bundle
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.BaseActivity
import com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen.BridgeDetailsFragment
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesListFragment
import com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen.BridgesInMapFragment

class MainActivity : BaseActivity(),
    MainActivityMvpView,
    BridgesListFragment.OnBridgesListFragmentInteractionListener,
    BridgeDetailsFragment.OnBridgeDetailsFragmentInteractionListener,
    BridgesInMapFragment.OnBridgesInMapFragmentInteractionListener {

    /*companion object {

//        fun createStartIntent(context: Context): Intent {
//            return Intent(context, MainActivity::class.java)
//        }
    }*/

    // todo: посмотреть как избавиться от знаков вопроса
    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = getApplicationComponents().provideMainActivityPresenter()

        mainActivityPresenter.attachView(this)
        mainActivityPresenter.onCreate()

    }

    override fun onDestroy() {
        mainActivityPresenter.detachView()
        super.onDestroy()
    }

    override fun showBridgesListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutFragmentContainer, BridgesListFragment.newInstance())
            .commit()
    }

    override fun showBridgeDetailsFragment(bridgeId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutFragmentContainer, BridgeDetailsFragment.newInstance(bridgeId))
            .addToBackStack(null)
            .commit()
    }

    override fun showBridgesInMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutFragmentContainer, BridgesInMapFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


    //region BridgesListFragment Callbacks
    override fun bridgeItemClicked(bridgeId: Int) {
        mainActivityPresenter.onBridgeItemClicked(bridgeId)
    }

    override fun menuMapItemClicked() {
        mainActivityPresenter.onMenuMapItemClicked()
    }
    //endregion

    //region BridgesInMapFragment callbacks
    override fun menuListItemClicked() {
        mainActivityPresenter.onMenuListItemClicked()
    }

    override fun bridgeItemMapClicked(bridgeId: Int) {
        mainActivityPresenter.onBridgeItemClicked(bridgeId)
    }
    //endregion
}


