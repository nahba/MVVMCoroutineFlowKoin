@file:Suppress("unused")

package moc.nahba.github

import android.app.Application
import moc.nahba.github.dependencies.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GitHub : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@GitHub)
            modules(koinModules())
        }
    }
    
    private fun koinModules() =
        listOf(
            ManagerModule, HelperModule,
            NetworkModule, ServiceModule,
            ModelModule, ViewModelModule,
        )
}