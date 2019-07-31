package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import androidx.annotation.NonNull
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.provider.BridgesRepository
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BridgesListFragmentPresenter(/*@NonNull private val bridgesRepository: BridgesRepository*/) : BasePresenter<BridgesListMvpView>() {

    private var disposable: Disposable? = null

    fun onCreate(){
        getMvpView()?.showProgress()
        getBridgesList()
    }

    fun onRefresh(){
        getBridgesList()
    }

    private fun getBridgesList() {
        disposable = bridgesRepository?.getBridges()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ bridges ->
                BridgesData.getInstance()?.setBridgesList(bridges)
                getMvpView()?.showBridges(bridges)
            },
                { getMvpView()?.showErrorState() }
            )
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}