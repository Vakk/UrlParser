package github.vakk.testtask.ui.main

import com.arellomobile.mvp.InjectViewState
import github.vakk.testtask.app.App
import github.vakk.testtask.model.manager.search.ISearchManager
import github.vakk.testtask.ui.common.BaseRxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Valery Kotsulym on 3/20/18.
 */
@InjectViewState
class MainPresenter : BaseRxPresenter<MainView>() {

    @Inject
    lateinit var searchManager: ISearchManager

    init {
        App.instance.daggerManager.searchComponent().inject(this)
    }

    fun search(url: String, query: String, threadsCount: Int) {
        addSubscription(searchManager.search(url, query, threadsCount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.newResultAppeared(it)
                }, { throwable ->
                    throwable.printStackTrace()
                }))
    }

}