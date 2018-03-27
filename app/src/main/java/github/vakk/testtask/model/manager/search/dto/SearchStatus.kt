package github.vakk.testtask.model.manager.search.dto

import github.vakk.testtask.common.Type

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
data class SearchStatus(
        var wordsFound: Int,
        var status: Type.ProcessStatus)