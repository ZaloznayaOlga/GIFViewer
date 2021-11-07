package zaloznaya.olga.app.gifviewer.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.data.device.db.ImagesDatabase

val DataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ImagesDatabase::class.java,
            "imagesDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<ImagesDatabase>().imagesDao()
    }
}