package com.cloniamix.lesson12engurazovkotlin.ui

import androidx.annotation.NonNull
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.provider.BridgesRepository
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(/*@NonNull private val bridgesRepository: BridgesRepository*/) :
    BasePresenter<MainActivityMvpView>() {

    private var disposable: Disposable? = null

    fun onCreate() {
        checkViewAttached()
        getMvpView()?.showBridgesListFragment()
        //getMvpView()?.showProgress()
        //getBridgesList()
    }

    /*private fun getBridgesList() {
        disposable = bridgesRepository?.getBridges()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ bridges ->
                BridgesData.getInstance()?.setBridgesList(bridges)
                getMvpView()?.showData()
                getMvpView()?.showBridgesListFragment()
            },
                { getMvpView()?.showErrorState() }
            )
    }*/

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}