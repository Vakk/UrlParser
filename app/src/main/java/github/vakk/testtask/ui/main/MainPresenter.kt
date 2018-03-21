package github.vakk.testtask.ui.main

import github.vakk.testtask.app.App
import github.vakk.testtask.model.manager.search.ISearchManager
import github.vakk.testtask.ui.common.BaseRxPresenter
import javax.inject.Inject

/**
 * Created by Valery Kotsulym on 3/20/18.
 */
class MainPresenter : BaseRxPresenter<MainView>() {

    @Inject
    lateinit var searchManager: ISearchManager

    init {
        App.instance.daggerManager.searchComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

}