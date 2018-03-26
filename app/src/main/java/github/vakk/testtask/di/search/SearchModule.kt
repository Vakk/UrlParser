package github.vakk.testtask.di.search

import dagger.Module
import dagger.Provides
import github.vakk.testtask.model.manager.search.ISearchManager
import github.vakk.testtask.model.manager.search.SearchManager
import github.vakk.testtask.model.services.RestService

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
@Module
class SearchModule {

    @Provides
    @Search
    fun searchManager(service: RestService): ISearchManager = SearchManager(service)

}