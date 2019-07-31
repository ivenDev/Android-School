package com.cloniamix.lesson12engurazovkotlin.ui

import android.os.Bundle
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.ui.base.BaseActivity
import com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen.BridgeDetailsFragment
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesListFragment
import com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen.BridgesInMapFragment

class MainActivity : BaseActivity(),
    MainActivityMvpView,
        BridgesListFragment.OnBridgesListFragmentInteractionListener
{

    companion object {
//        private const val FLAG_PROGRESS = 0
//        private const val FLAG_ERROR = 1
//        private const val FLAG_DATA = 2

        const val STATUS_NORMAL: Int = 1 // мост сведен
        const val STATUS_SOON: Int = 2 //мост скоро разведется
        const val STATUS_LATE: Int = 3 // мост разведен

//        fun createStartIntent(context: Context): Intent {
//            return Intent(context, MainActivity::class.java)
//        }
    }

    // todo: посмотреть как избавиться от знаков вопроса
    private lateinit var mainActivityPresenter: MainActivityPresenter

    //private var bridgesList: List<Bridge>? = null

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

    override fun showBridgeDetailsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutFragmentContainer, BridgeDetailsFragment.newInstance())
            .commit()
    }

    override fun showBridgesInMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutFragmentContainer, BridgesInMapFragment.newInstance())
            .commit()
    }

//    override fun showProgress() {
//        viewFlipperStates.displayedChild = FLAG_PROGRESS
//    }
//
//    override fun showErrorState() {
//        viewFlipperStates.displayedChild = FLAG_ERROR
//    }
//
//    override fun showData() {
//        viewFlipperStates.displayedChild = FLAG_DATA
//        //  отобразить нужный фрагмент с данными?
//    }

//    override fun setBridgesList(bridges: List<Bridge>){
//        this.bridgesList = bridges
//    }

}
