package github.vakk.testtask.app

import github.vakk.testtask.di.app.AppComponent
import github.vakk.testtask.di.search.SearchComponent

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
class DaggerManager(val appComponent: AppComponent) : IDaggerManager {

    var searchComponent: SearchComponent? = null

    @Synchronized
    override fun searchComponent(): SearchComponent = searchComponent ?: initSearchComponent()

    override fun releaseSearchComponent() {
        searchComponent = null
    }

    private fun initSearchComponent(): SearchComponent {
        val newComponent = appComponent.addSearchComponent()
        searchComponent = newComponent
        return newComponent
    }

    override fun appComponent(): AppComponent = appComponent
}