package github.vakk.testtask.ui.common

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
abstract class BaseActivity : Activity() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}