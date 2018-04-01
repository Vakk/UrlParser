package github.vakk.testtask.common

/**
 * Created by Valery Kotsulym on 3/26/18.
 */
object Type {
    enum class ItemProcessStatus {
        DOWNLOADING, PROCESSING, FOUND, NOT_FOUND, ERROR, UNKNOWN
    }

    enum class SearchStatus {
        STARTED, PAUSED, STOPPED, FINISHED, UNKNOWN
    }
}