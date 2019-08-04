package com.cloniamix.lesson12engurazovkotlin.ui.base

import android.text.format.DateUtils
import androidx.annotation.NonNull
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.Divorce
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


abstract class BasePresenter<T : MvpView> : Presenter<T> {

    companion object {
        const val FLAG_PROGRESS = 0
        const val FLAG_ERROR = 1
        const val FLAG_DATA = 2

        const val STATUS_NORMAL: Int = 1 // мост сведен
        const val STATUS_SOON: Int = 2 //мост скоро разведется
        const val STATUS_LATE: Int = 3 // мост разведен

        //fixme: подумать как обыграть (сделать эти методы протектед, а в статике вызывать их через другие методы)
        //нужны чтобы вызвать их в адаптере
        fun getStringDivorceTime(divorceTimesList: List<Divorce>): String {
            val formatter = SimpleDateFormat("h:mm")
            var divorceTime = ""
            for (divorce in divorceTimesList) {
                divorceTime =
                    divorceTime + formatter.format(divorce.start) + " - " + formatter.format(divorce.end) + "  "
            }
            return divorceTime
        }

        fun getBridgeStatus(divorceTimesList: List<Divorce>): Int {

            var bridgeStatus = STATUS_NORMAL

            val currentTime = Calendar.getInstance()

            for (divorce in divorceTimesList) {

                val startTime: Calendar = Calendar.getInstance()
                startTime.timeInMillis = divorce.start.time
                startTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                val endTime: Calendar = Calendar.getInstance()
                endTime.timeInMillis = divorce.end.time
                endTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                if (bridgeStatus != STATUS_LATE && startTime.timeInMillis - currentTime.timeInMillis in 0..DateUtils.HOUR_IN_MILLIS)
                    bridgeStatus = STATUS_SOON

                if (currentTime.after(startTime) && currentTime.before(endTime))
                    bridgeStatus = STATUS_LATE
            }

            return bridgeStatus
        }
    }

    private var mvpView: T? = null

    @NonNull
    protected val bridgesRepository = ApplicationComponents.getInstance()?.provideBridgesRepository()
    private var disposable: Disposable? = null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
        if (disposable != null) {
            disposable?.dispose()
        }
        doUnsubscribe()
    }


    protected fun isViewAttached(): Boolean {
        return mvpView != null
    }

    protected fun getMvpView(): T? {
        return mvpView
    }

    protected fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }


    //todo: реализовать метод получения по id
    protected fun getBridgesList() {
        disposable = bridgesRepository?.getBridges()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ bridges -> doInOk(bridges)},
                { t -> doError(t) }
            )
    }

    protected abstract fun doUnsubscribe()

    //fixme: подумать как обозвать эти методы понятнее
    protected abstract fun doInOk(bridges: List<Bridge>)
    protected abstract fun doError(t: Throwable)

    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")
}