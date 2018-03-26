package github.vakk.testtask.ui.main

import github.vakk.testtask.model.manager.search.SearchResult
import github.vakk.testtask.ui.common.BaseView

/**
 * Created by Valery Kotsulym on 3/20/18.
 */
interface MainView : BaseView {
    fun searchStarted()

    fun searchFinished()

    fun newResultAppeared(result: SearchResult)
}