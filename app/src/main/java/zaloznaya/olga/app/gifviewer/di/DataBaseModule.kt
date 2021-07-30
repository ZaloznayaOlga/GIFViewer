package zaloznaya.olga.app.gifviewer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zaloznaya.olga.app.gifviewer.data.device.db.ImagesDatabase
import zaloznaya.olga.app.gifviewer.data.device.db.dao.ImagesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): ImagesDatabase = Room.databaseBuilder(
            appContext,
            ImagesDatabase::class.java,
            "imagesDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideImagesDao(database: ImagesDatabase): ImagesDao = database.imagesDao()

}