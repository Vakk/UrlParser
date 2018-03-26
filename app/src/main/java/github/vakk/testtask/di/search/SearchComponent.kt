package github.vakk.testtask.di.search

import dagger.Subcomponent
import github.vakk.testtask.ui.main.MainPresenter

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
@Subcomponent(modules = [SearchModule::class])
@Search
interface SearchComponent {
    fun inject(presenter: MainPresenter)
}