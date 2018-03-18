package github.vakk.testtask.ui.common

import com.arellomobile.mvp.MvpFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
class BaseFragment : MvpFragment() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}