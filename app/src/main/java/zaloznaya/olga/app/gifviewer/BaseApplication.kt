package zaloznaya.olga.app.gifviewer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import zaloznaya.olga.app.gifviewer.di.*

class BaseApplication : Application () {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(appModule)
        }
    }
}

val appModule = listOf(
    NetworkModule,
    RepositoryModule,
    MappersModule,
    DataSourceModule,
    DataBaseModule,
    ViewModelModule,
    UseCasesModule
)