package github.vakk.testtask.model.manager.search

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
data class SearchResult(
        var id: String,
        var searchStatus: SearchStatus,
        var processed: Int,
        var size: Int,
        var urlName: String,
        var currentProcess: String,
        var itemsFound: Int
)