package github.vakk.testtask.model.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import github.vakk.testtask.app.App
import github.vakk.testtask.common.Type
import github.vakk.testtask.model.dto.SearchStatus
import github.vakk.testtask.model.manager.search.ISearchManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * Created by Valery Kotsulym on 4/1/18.
 */
class SearchService : Service() {

    private val binder = LocalBinder()

    private var searchStatus = SearchStatus()
    private val subject: Subject<SearchStatus>
    @Inject
    lateinit var searchManager: ISearchManager
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    init {
        subject = BehaviorSubject.create()
        subject.onNext(searchStatus)
    }

    override fun onCreate() {
        super.onCreate()
        App.instance.daggerManager.searchComponent?.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun searchResultsObservable(): Observable<SearchStatus> {
        return subject
    }

    fun startSearch(url: String, query: String, maxThreads: Int, maxNodes: Int) {
        searchStatus = SearchStatus(processStatus = Type.SearchStatus.STARTED)
        compositeDisposable?.add(
                searchManager.start(url, query, maxThreads, maxNodes)
                        .subscribe(
                                { onSearchNext(it) },
                                { onSearchError(it) })
        )
        emmitUpdate()
    }

    private fun onSearchNext(serchStatus: SearchStatus) {
        subject.onNext(serchStatus)
    }

    private fun onSearchError(throwable: Throwable) {
        subject.onError(throwable)
    }

    fun stopSearch() {
        searchStatus = SearchStatus(processStatus = Type.SearchStatus.STARTED)
        emmitUpdate()
    }

    private fun emmitUpdate() {
        subject.onNext(searchStatus)
    }

    inner class LocalBinder : Binder() {
        fun getService(): SearchService? {
            return this@SearchService
        }
    }

}