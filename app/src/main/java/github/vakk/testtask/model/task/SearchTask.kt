package github.vakk.testtask.model.task

import github.vakk.testtask.model.manager.search.SearchResult
import github.vakk.testtask.model.services.RestService
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Valery Kotsulym on 3/23/18.
 * Represents search engine with prepared params.
 */
class SearchTask @JvmOverloads constructor(
        val restService: RestService,
        val startUrl: String,
        val searchQuery: String,
        val maxDeep: Int = 10,
        val threadsCount: Int = 10
) {
    val executor: Executor

    init {
        executor = Executors.newFixedThreadPool(threadsCount)
    }

    /**
     * Starts search. Invoke task for start searching.
     */
    fun startSearch() {
        searchNext(startUrl)
    }

    fun searchNext(url: String) {
        Schedulers.from(executor)
    }

    /**
     * Makes pause of searching. Remember task and execute when all will be restored.
     */
    fun pauseSearch() {

    }

    /**
     * Releases all resources and completely interrupt search.
     */
    fun stopSearch() {

    }

    interface Listener {
        fun onUpdate(searchResult: SearchResult)

        fun onAdd(searchResult: SearchResult)

    }
}