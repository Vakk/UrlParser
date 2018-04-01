package github.vakk.testtask.ui.main

import com.arellomobile.mvp.InjectViewState
import github.vakk.testtask.app.App
import github.vakk.testtask.common.Type
import github.vakk.testtask.model.dto.SearchStatus
import github.vakk.testtask.model.services.SearchService
import github.vakk.testtask.ui.common.BaseRxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Valery Kotsulym on 3/20/18.
 */
@InjectViewState
class MainPresenter : BaseRxPresenter<MainView>() {

    private var searchService: SearchService? = null
    private var lastStatus: SearchStatus? = null

    init {
        App.instance.daggerManager.searchComponent().inject(this)
    }

    fun connect(service: SearchService?) {
        searchService = service
        searchService?.let {
            it.searchResultsObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { status -> onSearchNext(status) },
                            { throwable -> onSearchError(throwable) }
                    )
        }
    }

    fun disconnect() {
        searchService = null
    }

    private fun onSearchNext(searchStatus: SearchStatus) {
        lastStatus = searchStatus
        viewState.changeData(searchStatus.resultList)
        when (searchStatus.processStatus) {
            Type.SearchStatus.STARTED -> {
                viewState.searchStarted()
            }
            else -> {
                viewState.searchFinished()
            }
        }
    }

    private fun onSearchError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    fun start(url: String, query: String, maxThreads: Int, maxNodes: Int) {
        searchService?.startSearch(url, query, maxThreads, maxNodes)
    }

    fun stop() {
        searchService?.stopSearch()
    }

    fun searchStarted(): Boolean = lastStatus?.processStatus == Type.SearchStatus.STARTED


}