package github.vakk.testtask.model.dto

import github.vakk.testtask.common.Type

/**
 * Created by Valery Kotsulym on 4/1/18.
 */
data class SearchStatus(
        var result: MutableSet<SearchResultItem> = HashSet(),
        var processStatus: Type.SearchStatus = Type.SearchStatus.UNKNOWN
) {
    val resultList: List<SearchResultItem> get() = result.toList()
}