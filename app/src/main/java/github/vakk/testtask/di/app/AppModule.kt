package github.vakk.testtask.di.app

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
@Module
class AppModule constructor(
        private val context: Context
) {
    @Provides
    @Application
    fun context(): Context = context
}