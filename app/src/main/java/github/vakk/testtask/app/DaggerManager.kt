package github.vakk.testtask.app

import github.vakk.testtask.di.app.AppComponent
import github.vakk.testtask.di.network.NetworkModule
import github.vakk.testtask.di.search.SearchComponent
import github.vakk.testtask.di.search.SearchModule

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
class DaggerManager(val appComponent: AppComponent) : IDaggerManager {

    var searchComponent: SearchComponent? = null

    override fun searchComponent(): SearchComponent = searchComponent ?: initSearchComponent()

    override fun releaseSearchComponent() {
        searchComponent = null
    }

    private fun initSearchComponent(): SearchComponent {
        val newComponent = appComponent.addSearchComponent(SearchModule())
        searchComponent = newComponent
        return newComponent
    }

    override fun appComponent(): AppComponent = appComponent
}