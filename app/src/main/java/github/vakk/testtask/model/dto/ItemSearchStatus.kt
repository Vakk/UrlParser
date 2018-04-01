package github.vakk.testtask.model.dto

import github.vakk.testtask.common.Type

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
data class ItemSearchStatus(
        var wordsFound: Int,
        var status: Type.ItemProcessStatus)