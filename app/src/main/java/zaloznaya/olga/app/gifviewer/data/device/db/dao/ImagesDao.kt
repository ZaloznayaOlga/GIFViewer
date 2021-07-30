package zaloznaya.olga.app.gifviewer.data.device.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGifImages(images: List<GifImageEntity>)

    @Query("SELECT * FROM images WHERE marked_as_deleted = 0")
    fun getGifImages(): Flow<List<GifImageEntity>>

    @Query("SELECT id FROM images WHERE marked_as_deleted = 1")
    suspend fun getDeletedImagesIds(): List<String>

    @Query("UPDATE images SET marked_as_deleted = 1 WHERE id = :id")
    suspend fun markAsDeleted(id: String)

}