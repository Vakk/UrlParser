package github.vakk.testtask.di.search

import dagger.Module
import dagger.Provides
import github.vakk.testtask.model.manager.search.ISearchManager
import github.vakk.testtask.model.manager.search.SearchManager

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
@Module
class SearchModule {

    @Provides
    @SearchScope
    fun searchManager(): ISearchManager = SearchManager()

}