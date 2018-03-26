package github.vakk.testtask.di.app

import android.content.Context
import dagger.Component
import github.vakk.testtask.di.network.Network
import github.vakk.testtask.di.network.NetworkModule
import github.vakk.testtask.di.search.SearchComponent
import github.vakk.testtask.di.search.SearchModule

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
@Component(modules = [AppModule::class, NetworkModule::class])
@Application
@Network
interface AppComponent {

    fun addSearchComponent(module: SearchModule): SearchComponent

    fun context(): Context
}