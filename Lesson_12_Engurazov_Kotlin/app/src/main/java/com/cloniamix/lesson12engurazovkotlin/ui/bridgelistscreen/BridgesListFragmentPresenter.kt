package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BridgesListFragmentPresenter : BasePresenter<BridgesListMvpView>() {

    private var disposable: Disposable? = null
    private var bridges: List<Bridge> = BridgesData.getInstance()!!.getBridgesList()

    fun onViewCreated(){
        checkViewAttached()
        getMvpView()?.showState(FLAG_PROGRESS)

        //todo: выполнить проверку в каждом презентере
        if (bridges.isEmpty()){
            getBridgesList()
        } else {
            updateUi(bridges)
        }
    }

    fun onRefresh(){
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgesList()
    }

    fun onSwipeRefresh(){
        getBridgesList()
    }

    /*private fun getBridgesList() {
        disposable = bridgesRepository?.getBridges()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ bridges -> updateUi(bridges)},
                { t -> error(t) }
            )
    }*/

    override fun doInOk(bridges: List<Bridge>) {
        BridgesData.getInstance()?.setBridgesList(bridges)
        updateUi(bridges)
    }

    override fun doError(t: Throwable) {
        error(t)
    }

    private fun updateUi(bridges: List<Bridge>){
        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_DATA)
            getMvpView()?.showBridges(bridges)
        }
    }

    private fun error(t: Throwable) {

        Log.d("MyTag", t.toString())

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_ERROR)
        }
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}