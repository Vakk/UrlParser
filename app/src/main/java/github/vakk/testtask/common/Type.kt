package github.vakk.testtask.common

/**
 * Created by Valery Kotsulym on 3/26/18.
 */
object Type {
    enum class ProcessStatus {
        DOWNLOADING, PROCESSING, FOUND, NOT_FOUND, ERROR, UNKNOWN
    }
}