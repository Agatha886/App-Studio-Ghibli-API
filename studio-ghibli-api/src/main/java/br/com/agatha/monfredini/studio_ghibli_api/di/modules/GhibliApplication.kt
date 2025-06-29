package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class GhibliApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GhibliApplication)
            modules(listOf(ghibliApiRepository, ghibliApiViewModel))
        }
    }
}