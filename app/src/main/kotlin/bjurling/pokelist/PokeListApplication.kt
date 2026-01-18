package bjurling.pokelist

import android.app.Application
import bjurling.pokelist.data.di.dataModule
import bjurling.pokelist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokeListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(androidContext = this@PokeListApplication)
            modules(appModule, dataModule)
        }
    }
}
