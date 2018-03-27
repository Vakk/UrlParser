package github.vakk.testtask.model.manager.search

import github.vakk.testtask.common.Type
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.model.manager.search.dto.SearchStatus
import github.vakk.testtask.model.services.RestService
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
private const val URL_PATTERN = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?\$"

class SearchManager(val searchService: RestService) : ISearchManager {

    private var fixedThreadExecutor = Executors.newFixedThreadPool(10, {
        Thread(it)
    })

    private var singleExecutor = Executors.newSingleThreadExecutor()

    private val searchSubject = PublishSubject.create<SearchResultItem>()
    private var maximumNodeDeepProcess = 5

    @Volatile
    private var paused = false

    override fun start(url: String, term: String, threadsCount: Int, maxDeep: Int) {
        maximumNodeDeepProcess = maxDeep
        fixedThreadExecutor = Executors.newFixedThreadPool(threadsCount, {
            Thread(it)
        })
        onNext(url, term, AtomicInteger(0), AtomicInteger(0))
    }

    /**
     * Begins process for next node level.
     */
    private fun onNext(url: String, query: String, nodeDeep: AtomicInteger, nodePosition: AtomicInteger) {
        fixedThreadExecutor.execute {
            val result = createNewSearchResult(url, query)
            try {

                result.searchStatus.status = Type.ProcessStatus.DOWNLOADING
                searchSubject.onNext(result)
                val page = downloadPage(url)

                result.searchStatus.status = Type.ProcessStatus.PROCESSING
                searchSubject.onNext(result)
                processPage(page, query, nodeDeep, nodePosition, result)
            } catch (throwable: Throwable) {
                result.searchStatus.status = Type.ProcessStatus.ERROR
                searchSubject.onNext(result)
            }
        }
    }

    private fun downloadPage(url: String) = searchService.getBody(url).execute().body()?.string()
            ?: ""

    private fun processPage(source: String, query: String,
                            nodeDeep: AtomicInteger,
                            nodePosition: AtomicInteger,
                            searchResultItem: SearchResultItem) {
        singleExecutor.execute {
            try {
                searchQuery(source, query, searchResultItem)
                if (nodeDeep.get() < maximumNodeDeepProcess)
                    searchUrl(source, query, nodeDeep, nodePosition)
            } catch (throwable: Throwable) {
                processError(throwable, searchResultItem)
            }
        }
    }

    private fun processError(throwable: Throwable, searchResultItem: SearchResultItem) {
        searchResultItem.searchStatus.status = Type.ProcessStatus.ERROR
        searchResultItem.error = Error(throwable)
    }

    private fun searchQuery(source: String,
                            query: String,
                            searchResultItem: SearchResultItem) {
        val regex = Regex("\\($query\\)")
        val pattern = regex.toPattern()
        searchResultItem.searchStatus.wordsFound = pattern.matcher(source).toMatchResult().groupCount()
        if (searchResultItem.searchStatus.wordsFound > 0) {
            searchResultItem.searchStatus.status = Type.ProcessStatus.FOUND
        } else {
            searchResultItem.searchStatus.status = Type.ProcessStatus.NOT_FOUND
        }
        searchSubject.onNext(searchResultItem)
    }

    private fun searchUrl(source: String,
                          query: String,
                          nodeDeep: AtomicInteger,
                          nodePosition: AtomicInteger) {
        val regex = Regex(URL_PATTERN)
        val pattern = regex.toPattern()
        val matcher = pattern.matcher(source)
        nodeDeep.incrementAndGet()
        for (position in 0..matcher.groupCount()) {
            nodePosition.incrementAndGet()
            onNext(matcher.group(position), query, nodeDeep, nodePosition)
        }

    }

    private fun createNewSearchResult(url: String, query: String): SearchResultItem {
        return SearchResultItem(UUID.randomUUID().toString(), url, query,
                SearchStatus(0, Type.ProcessStatus.UNKNOWN)
        )
    }

    override fun pause() {
        paused = true
    }

    override fun stop() {
        fixedThreadExecutor.shutdown()
        singleExecutor.shutdown()
    }

    override fun resultObservable() = searchSubject

    override fun isPaused() = paused
}