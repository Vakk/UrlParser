package github.vakk.testtask.model.manager.search

import github.vakk.testtask.model.services.RestService
import github.vakk.testtask.model.task.SearchTask
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
class SearchManager(val searchService: RestService) : ISearchManager {

    private var executor: Executor
    private var scheduler: Scheduler
    private val searchResultSubject: Subject<SearchResult> = PublishSubject.create()

    var threadCount = 1
    var searchDeeps = 1
    var searchTask: SearchTask? = null

    init {
        executor = recreateExecutor()
        scheduler = recreateScheduler()
    }

    override fun setThreadsCount(threadsCount: Int) {
        threadCount = threadsCount
        executor = recreateExecutor()
        scheduler = recreateScheduler()
    }

    private fun recreateExecutor(): Executor = Executors.newFixedThreadPool(threadCount)

    private fun recreateScheduler(): Scheduler = Schedulers.from(executor)

    override fun search(url: String, term: String, maxDeep: Int): Observable<SearchResult> {
        cancelLastSearch()
        searchTask = SearchTask(searchService, url, term)
        searchTask?.startSearch()
        return searchResultSubject
    }

    private fun cancelLastSearch() {
        searchTask?.stopSearch()
        searchTask = null
    }

}