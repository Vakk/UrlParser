package github.vakk.testtask.model.manager.search

import github.vakk.testtask.common.Type
import github.vakk.testtask.model.dto.ItemSearchStatus
import github.vakk.testtask.model.dto.SearchResultItem
import github.vakk.testtask.model.dto.SearchStatus
import github.vakk.testtask.model.executors.DownloadPageExecutor
import github.vakk.testtask.model.executors.ProcessPageExecutor
import github.vakk.testtask.model.services.RestService
import github.vakk.testtask.model.tasks.PageLoadTask
import github.vakk.testtask.model.tasks.TermSearchTask
import github.vakk.testtask.model.tasks.UrlSearchTask
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class NewSearchManager(private val restService: RestService) : ISearchManager {
    private var compositeDisposable = CompositeDisposable()
    override fun pause() {

    }

    override fun stop() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }

    override fun isPaused(): Boolean = false

    private var pageLoadTask = PageLoadTask(Executors.newSingleThreadExecutor())
    private var termSearchTask = TermSearchTask(Executors.newSingleThreadExecutor())
    private var urlSearchTask = UrlSearchTask(Executors.newSingleThreadExecutor())
    private var maxNodes = 1

    override fun start(url: String, query: String, downloadingThreadsCount: Int, nodesCount: Int): Observable<SearchStatus> {
        stop()
        this.maxNodes = nodesCount
        initTasks(downloadingThreadsCount)
        val searchStatus = SearchStatus()

        return Observable.create<SearchResultItem> { beginProcessPage(url, query, it) }
                .doOnNext { item -> searchStatus.result.add(item) }
                .map { searchStatus }
    }

    private fun beginProcessPage(url: String, query: String, observer: ObservableEmitter<SearchResultItem>) {
        processPage(url, query, AtomicInteger(0), observer)
    }

    private fun processPage(url: String, query: String, nodes: AtomicInteger, observer: ObservableEmitter<SearchResultItem>) {
        val searchResultItem = SearchResultItem(UUID.randomUUID().toString(), url, query, ItemSearchStatus(0, Type.ItemProcessStatus.DOWNLOADING))
        observer.onNext(searchResultItem)

        compositeDisposable.add(
                loadPage(url)
                        .doOnNext {
                            searchResultItem.searchStatus.status = Type.ItemProcessStatus.PROCESSING
                            observer.onNext(searchResultItem)
                        }
                        .concatMap({ pageSource ->
                            searchTermMatches(pageSource, query)
                                    .doOnNext({ matches -> searchResultItem.searchStatus.wordsFound = matches })
                                    .map { pageSource }
                        })
                        .concatMap({ pageSource ->
                            searchUrl(pageSource)
                                    .takeWhile { nodes.incrementAndGet() < maxNodes }
                                    .doOnNext { pageUrl -> processPage(pageUrl, query, nodes, observer) } // do recursive call for another node.
                        })
                        .map { searchResultItem }
                        .onErrorReturn {
                            searchResultItem.error = it
                            searchResultItem.searchStatus.status = Type.ItemProcessStatus.ERROR
                            searchResultItem
                        }
                        .doOnNext {
                            it.searchStatus.status = when {
                                it.searchStatus.status == Type.ItemProcessStatus.ERROR -> Type.ItemProcessStatus.ERROR
                                it.searchStatus.wordsFound > 0 -> Type.ItemProcessStatus.FOUND
                                else -> Type.ItemProcessStatus.NOT_FOUND
                            }
                        }
                        .subscribe(observer::onNext)
        )
    }

    private fun loadPage(url: String): Observable<String> {
        return pageLoadTask.execute(url, restService)
    }

    private fun searchUrl(pageSource: String): Observable<String> {
        return urlSearchTask.execute(pageSource)
    }

    private fun searchTermMatches(source: String, query: String): Observable<Int> {
        return termSearchTask.execute(source, query)
    }

    private fun initTasks(maxLoadThreads: Int) {
        val downloadPageExecutor = DownloadPageExecutor(maxLoadThreads)
        val processPageExecutor = ProcessPageExecutor()

        pageLoadTask = PageLoadTask(downloadPageExecutor)
        termSearchTask = TermSearchTask(processPageExecutor)
        urlSearchTask = UrlSearchTask(processPageExecutor)
    }

}