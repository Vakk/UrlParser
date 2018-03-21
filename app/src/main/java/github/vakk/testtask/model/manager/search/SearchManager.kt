package github.vakk.testtask.model.manager.search

import io.reactivex.Observable

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
class SearchManager : ISearchManager {

    override fun search(url: String, term: String, threadsCount: Int, maxDeep: Int): Observable<List<SearchResult>> {
        val result = ArrayList<SearchResult>()

        result.add(SearchResult(Observable.just(SearchStatus.STARTED),
                Observable.just(10),
                Observable.just(2),
                Observable.just("name"),
                Observable.just(10)
        ))

        return Observable.just(result)
    }

}