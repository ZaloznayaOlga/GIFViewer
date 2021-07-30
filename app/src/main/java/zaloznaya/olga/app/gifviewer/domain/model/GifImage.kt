package zaloznaya.olga.app.gifviewer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifImage(
    val id: String,
    val title: String,
    val urlPreview: String,
    val urlOriginal: String
) : Parcelable
