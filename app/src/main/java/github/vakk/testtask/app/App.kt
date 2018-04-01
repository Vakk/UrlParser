package github.vakk.testtask.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import github.vakk.testtask.di.app.AppModule
import github.vakk.testtask.di.app.DaggerAppComponent

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
class App : Application() {

    lateinit var daggerManager: DaggerManager

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        instance = this
        initAppComponent()
    }

    private fun initAppComponent() {
        daggerManager = DaggerManager(DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        )
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
    }

}