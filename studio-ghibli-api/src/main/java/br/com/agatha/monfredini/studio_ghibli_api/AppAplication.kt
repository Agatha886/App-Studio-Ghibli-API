package br.com.agatha.monfredini.studioghibli
import android.app.Application
import br.com.agatha.monfredini.studioghibli.di.modules.repository
import br.com.agatha.monfredini.studioghibli.di.modules.model
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppAplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppAplication)
            modules(listOf(repository, model))
        }
    }
}