package github.vakk.testtask.model.manager.search

import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import io.reactivex.Observable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
interface ISearchManager {
    fun start(url: String, term: String, threadsCount: Int, maxDeep: Int): Observable<SearchResultItem>

    fun pause()

    fun stop()

    fun isPaused(): Boolean
}