package github.vakk.testtask.ui.common

import android.support.v7.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Valery Kotsulym on 3/17/18.
 */
abstract class RxRecyclerAdapter<V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
    val subscription = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        subscription.add(disposable)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        subscription.dispose()
    }
}