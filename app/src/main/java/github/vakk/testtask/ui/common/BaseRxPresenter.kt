package github.vakk.testtask.ui.common

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
open class BaseRxPresenter : MvpPresenter<BaseView>() {
    val compositeDisposable = CompositeDisposable()

    protected fun addSubscription(subscription: Disposable) {
        compositeDisposable.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}