package github.vakk.testtask.di.app

import android.content.Context
import dagger.Component
import github.vakk.testtask.di.search.SearchComponent
import javax.inject.Singleton

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
@Component(modules = [(AppModule::class)])
@Singleton
interface AppComponent {
    fun addSearchComponent(): SearchComponent

    fun context(): Context
}