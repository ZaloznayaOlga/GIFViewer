package com.olgaz.gifviewer.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    @SerialName("type")
    val type: String,
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("bitly_gif_url")
    val bitlyGifUrl: String,
    @SerialName("bitly_url")
    val bitlyUrl: String,
    @SerialName("embed_url")
    val embedUrl: String,
    @SerialName("username")
    val username: String,
    @SerialName("source")
    val source: String,
    @SerialName("title")
    val title: String,
    @SerialName("rating")
    val rating: String,
    @SerialName("content_url")
    val contentUrl: String,
    @SerialName("source_tld")
    val sourceTld: String,
    @SerialName("source_post_url")
    val sourcePostUrl: String,
    @SerialName("is_sticker")
    val isSticker: Int,
    @SerialName("import_datetime")
    val importDatetime: String,
    @SerialName("trending_datetime")
    val trendingDatetime: String,
    @SerialName("images")
    val images: ImagesDto,
    @SerialName("analytics_response_payload")
    val analyticsResponsePayload: String,
    @SerialName("analytics")
    val analytics: AnalyticsDto?,
    @SerialName("alt_text")
    val altText: String?,
    @SerialName("is_low_contrast")
    val isLowContrast: Boolean
) {
    @Serializable
    data class ImagesDto(
        @SerialName("original")
        val original: Original?,
        @SerialName("downsized")
        val downsized: Downsized?,
        @SerialName("downsized_large")
        val downsizedLarge: DownsizedLarge?,
        @SerialName("downsized_medium")
        val downsizedMedium: DownsizedMedium?,
        @SerialName("downsized_small")
        val downsizedSmall: DownsizedSmall?,
        @SerialName("downsized_still")
        val downsizedStill: DownsizedStill?,
        @SerialName("fixed_height")
        val fixedHeight: FixedHeight?,
        @SerialName("fixed_height_downsampled")
        val fixedHeightDownsampled: FixedHeightDownsampled?,
        @SerialName("fixed_height_small")
        val fixedHeightSmall: FixedHeightSmall?,
        @SerialName("fixed_height_small_still")
        val fixedHeightSmallStill: FixedHeightSmallStill?,
        @SerialName("fixed_height_still")
        val fixedHeightStill: FixedHeightStill?,
        @SerialName("fixed_width")
        val fixedWidth: FixedWidth?,
        @SerialName("fixed_width_downsampled")
        val fixedWidthDownsampled: FixedWidthDownsampled?,
        @SerialName("fixed_width_small")
        val fixedWidthSmall: FixedWidthSmall?,
        @SerialName("fixed_width_small_still")
        val fixedWidthSmallStill: FixedWidthSmallStill?,
        @SerialName("fixed_width_still")
        val fixedWidthStill: FixedWidthStill?,
        @SerialName("looping")
        val looping: Looping?,
        @SerialName("original_still")
        val originalStill: OriginalStill?,
        @SerialName("original_mp4")
        val originalMp4: OriginalMp4?,
        @SerialName("preview")
        val preview: Preview?,
        @SerialName("preview_gif")
        val previewGif: PreviewGif?,
        @SerialName("preview_webp")
        val previewWebp: PreviewWebp?,
        @SerialName("480w_still")
        val wStill: WStill?
    ) {
        @Serializable
        data class Original(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String,
            @SerialName("frames")
            val frames: String,
            @SerialName("hash")
            val hash: String
        )

        @Serializable
        data class Downsized(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class DownsizedLarge(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class DownsizedMedium(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class DownsizedSmall(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String
        )

        @Serializable
        data class DownsizedStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class FixedHeight(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedHeightDownsampled(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedHeightSmall(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedHeightSmallStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class FixedHeightStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class FixedWidth(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedWidthDownsampled(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedWidthSmall(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String,
            @SerialName("webp_size")
            val webpSize: String,
            @SerialName("webp")
            val webp: String
        )

        @Serializable
        data class FixedWidthSmallStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class FixedWidthStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class Looping(
            @SerialName("mp4_size")
            val mp4Size: String?,
            @SerialName("mp4")
            val mp4: String?
        )

        @Serializable
        data class OriginalStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class OriginalMp4(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String
        )

        @Serializable
        data class Preview(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("mp4_size")
            val mp4Size: String,
            @SerialName("mp4")
            val mp4: String
        )

        @Serializable
        data class PreviewGif(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class PreviewWebp(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class WStill(
            @SerialName("height")
            val height: String,
            @SerialName("width")
            val width: String,
            @SerialName("size")
            val size: String,
            @SerialName("url")
            val url: String
        )
    }

    @Serializable
    data class AnalyticsDto(
        @SerialName("onload")
        val onload: Onload,
        @SerialName("onclick")
        val onclick: Onclick,
        @SerialName("onsent")
        val onsent: Onsent
    ) {
        @Serializable
        data class Onload(
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class Onclick(
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class Onsent(
            @SerialName("url")
            val url: String
        )
    }
}