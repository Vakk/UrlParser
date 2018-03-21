package github.vakk.testtask.model.manager.search

import io.reactivex.Observable

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
data class SearchResult(
        var searchStatus: Observable<SearchStatus>,
        var processed: Observable<Int>,
        var size: Observable<Int>,
        var urlName: Observable<String>,
        var itemsFound: Observable<Int>
)