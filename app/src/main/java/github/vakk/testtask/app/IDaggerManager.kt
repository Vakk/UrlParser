package github.vakk.testtask.app

import github.vakk.testtask.di.app.AppComponent
import github.vakk.testtask.di.search.SearchComponent

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
interface IDaggerManager {

    fun appComponent(): AppComponent

    fun searchComponent(): SearchComponent

    fun releaseSearchComponent()
}