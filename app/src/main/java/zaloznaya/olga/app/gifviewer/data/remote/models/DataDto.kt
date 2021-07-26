package zaloznaya.olga.app.gifviewer.data.remote.models

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("is_sticker")
    val isSticker: Int,
    @SerializedName("import_datetime")
    val importDatetime: String,
    @SerializedName("trending_datetime")
    val trendingDatetime: String,
    @SerializedName("images")
    val images: ImagesDto
)

data class ImagesDto(
    @SerializedName("preview_gif")
    val previewGif: GifDto,
    @SerializedName("original")
    val original: GifDto,
)

data class GifDto(
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String
)
