package github.vakk.testtask.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
@Module
class AppModule constructor(
        private val context: Context
) {
    @Provides
    @Singleton
    fun context(): Context = context
}