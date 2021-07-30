package zaloznaya.olga.app.gifviewer.data.device.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GifImageEntity.TABLE_NAME)
class GifImageEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url_preview")
    val urlPreview: String,
    @ColumnInfo(name = "url_original")
    val urlOriginal: String,
    @ColumnInfo(name = "marked_as_deleted")
    var markedAsDeleted: Boolean
) {
    companion object {
        const val TABLE_NAME = "images"
    }
}