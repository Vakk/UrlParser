package github.vakk.testtask.model.manager.search

import github.vakk.testtask.model.dto.SearchStatus
import io.reactivex.Observable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
interface ISearchManager {
    fun start(url: String, query: String, downloadingThreadsCount: Int, nodesCount: Int): Observable<SearchStatus>

    fun pause()

    fun stop()

    fun isPaused(): Boolean
}