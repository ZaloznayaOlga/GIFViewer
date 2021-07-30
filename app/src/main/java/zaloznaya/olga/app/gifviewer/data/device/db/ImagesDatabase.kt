package zaloznaya.olga.app.gifviewer.data.device.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zaloznaya.olga.app.gifviewer.data.device.db.dao.ImagesDao
import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity

@Database(
    entities = [GifImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}