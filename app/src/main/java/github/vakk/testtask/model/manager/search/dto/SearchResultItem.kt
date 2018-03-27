package github.vakk.testtask.model.manager.search.dto

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
data class SearchResultItem(
        var id: String,
        var urlName: String,
        var query: String,
        var searchStatus: SearchStatus,
        var error: Throwable? = null
)