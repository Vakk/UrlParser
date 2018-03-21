package github.vakk.testtask.ui.common

import android.widget.Toast
import com.arellomobile.mvp.MvpActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
abstract class BaseActivity : MvpActivity(), BaseView {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }
}