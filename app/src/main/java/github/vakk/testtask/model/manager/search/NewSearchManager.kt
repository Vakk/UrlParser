package github.vakk.testtask.model.manager.search

import github.vakk.testtask.common.Type
import github.vakk.testtask.model.executors.DownloadPageExecutor
import github.vakk.testtask.model.executors.ProcessPageExecutor
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.model.manager.search.dto.SearchStatus
import github.vakk.testtask.model.services.RestService
import github.vakk.testtask.model.tasks.PageLoadTask
import github.vakk.testtask.model.tasks.TermSearchTask
import github.vakk.testtask.model.tasks.UrlSearchTask
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class NewSearchManager(private val restService: RestService) : ISearchManager {

    private val subject = PublishSubject.create<SearchResultItem>()

    @Volatile
    private var paused = false

    private var loadPageSource: PageLoadTask = PageLoadTask(DownloadPageExecutor(2))
    private var querySearchTask: TermSearchTask = TermSearchTask(Executors.newSingleThreadExecutor())
    private var urlSearchTask: UrlSearchTask = UrlSearchTask(Executors.newSingleThreadExecutor())

    private var maxNodes = 10

    override fun start(url: String, term: String, threadsCount: Int, maxDeep: Int)
            : Observable<SearchResultItem> {

        val processExecutor = ProcessPageExecutor()

        maxNodes = maxDeep
        loadPageSource = PageLoadTask(DownloadPageExecutor(threadsCount))
        querySearchTask = TermSearchTask(processExecutor)
        urlSearchTask = UrlSearchTask(processExecutor)

        beginProcessObservable(url, term)
                .subscribeWith(subject)
                .take(1)
                .flatMap { Observable.empty<SearchResultItem>() }

        return subject
    }

    private fun beginProcessObservable(url: String, query: String): Observable<SearchResultItem> {
        return processUrl(url, query, AtomicInteger(0))
    }

    private fun processUrl(url: String, query: String, node: AtomicInteger): Observable<SearchResultItem> {

        val resultItem = createResultItem(url, query)

        resultItem.searchStatus = SearchStatus(0, Type.ProcessStatus.DOWNLOADING)
        subject.onNext(resultItem)

        return loadPageSource.execute(url, restService)
                .doOnError({
                    resultItem.error = it
                    resultItem.searchStatus.status = Type.ProcessStatus.ERROR
                    subject.onNext(resultItem)
                })
                .doOnNext({
                    resultItem.searchStatus = SearchStatus(0, Type.ProcessStatus.PROCESSING)
                    subject.onNext(resultItem)
                }).concatMap { searchObservable(it, query, resultItem, node) }
                .doOnNext {
                    if (it.searchStatus.wordsFound > 0) {
                        it.searchStatus.status = Type.ProcessStatus.FOUND
                    } else {
                        it.searchStatus.status = Type.ProcessStatus.NOT_FOUND
                    }
                    subject.onNext(it)
                }
                .map { resultItem }

    }

    private fun searchObservable(body: String, query: String, resultItem: SearchResultItem, node: AtomicInteger)
            : Observable<SearchResultItem> {
        val searchTerm = querySearchTask.execute(body, query)
                .onErrorReturn {
                    resultItem.searchStatus.status = Type.ProcessStatus.ERROR
                    subject.onNext(resultItem)
                    resultItem.searchStatus.wordsFound
                }
                .doOnNext({
                    resultItem.searchStatus.wordsFound = it
                    subject.onNext(resultItem)
                })

        val searchUrl = urlSearchTask.execute(body)
                .doOnError {
                    resultItem.searchStatus.status = Type.ProcessStatus.ERROR
                    subject.onNext(resultItem)
                }.flatMap({
                    return@flatMap if (node.getAndIncrement() < maxNodes) {
                        processUrl(it, query, node)
                    } else {
                        Observable.just(resultItem)
                    }
                })

        return searchTerm.flatMap { searchUrl }
    }

    private fun createResultItem(url: String, term: String): SearchResultItem {
        return SearchResultItem(UUID.randomUUID().toString(), url, term, SearchStatus(0, Type.ProcessStatus.FOUND))
    }

    override fun pause() {
        paused = true
    }

    override fun stop() {

    }

    override fun isPaused(): Boolean = paused

}