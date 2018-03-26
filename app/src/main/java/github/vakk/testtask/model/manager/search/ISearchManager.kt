package github.vakk.testtask.model.manager.search

import android.support.annotation.IntRange
import io.reactivex.Observable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
interface ISearchManager {
    fun setThreadsCount(@IntRange(from = 1) threadsCount: Int)

    fun search(url: String, term: String, maxDeep: Int): Observable<SearchResult>
}