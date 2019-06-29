package com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloniamix.lesson_7_engurazov_kotlin.MyListener
import com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgelist.adapter.BridgesAdapter
import com.cloniamix.lesson_7_engurazov_kotlin.R
import com.cloniamix.lesson_7_engurazov_kotlin.Utils
import com.cloniamix.lesson_7_engurazov_kotlin.network.BridgeApiService
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridge
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridges
import com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgedetails.BridgeDetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyListener {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.title_text)

        showProgress(true)

        getBridgeListFromApi()

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onMyClick(bridge: Bridge) {
        startActivity(BridgeDetailsActivity.createStartIntent(this, bridge))
    }

    private fun getBridgeListFromApi(){
        disposable = BridgeApiService.getClient.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUi)
    }

    private fun updateUi(bridges: Bridges){

        showProgress(false)
        recyclerViewBridgeList.layoutManager = LinearLayoutManager(this)
        recyclerViewBridgeList.adapter = BridgesAdapter(bridges.objects, this)
    }

    private fun showProgress(show: Boolean){
        if (show) {
            Utils.setViewVisible(progressBar, true)
            Utils.setViewVisible(recyclerViewBridgeList, false)
        } else {
            Utils.setViewVisible(progressBar, false)
            Utils.setViewVisible(recyclerViewBridgeList, true)
        }
    }
}
