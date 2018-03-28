package github.vakk.testtask.ui.main

import com.arellomobile.mvp.InjectViewState
import github.vakk.testtask.app.App
import github.vakk.testtask.model.manager.search.ISearchManager
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.ui.common.BaseRxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Valery Kotsulym on 3/20/18.
 */
@InjectViewState
class MainPresenter : BaseRxPresenter<MainView>() {

    private val resultSet: MutableSet<SearchResultItem> = hashSetOf()

    @Inject
    lateinit var searchManager: ISearchManager

    init {
        App.instance.daggerManager.searchComponent().inject(this)
    }

    private fun onSearchResultAppeared(item: List<SearchResultItem>) {
        resultSet.removeAll(item)
        resultSet.addAll(item)
        viewState.changeData(resultSet.toList())
    }

    private fun onSearchErrorHappened(throwable: Throwable) {
        throwable.printStackTrace()
    }

    fun search(url: String, query: String, threadsCount: Int, maxDeep: Int) {
        addSubscription(searchManager.start(url, query, threadsCount, maxDeep)
                .buffer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    onSearchResultAppeared(items)
                }, { throwable ->
                    onSearchErrorHappened(throwable)
                })
        )
    }

}