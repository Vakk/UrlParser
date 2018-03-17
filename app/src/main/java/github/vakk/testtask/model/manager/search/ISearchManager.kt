package github.vakk.testtask.model.manager.search

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
interface ISearchManager {
    fun search(url: String, term: String, threadsCount: Int, maxDeep: Int)
}