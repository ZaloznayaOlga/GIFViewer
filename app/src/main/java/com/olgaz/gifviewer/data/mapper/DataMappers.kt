package com.olgaz.gifviewer.data.mapper

import com.olgaz.gifviewer.data.remote.models.DataDto
import com.olgaz.gifviewer.domain.model.GifImage

fun List<DataDto>.toGifImages(): List<GifImage> {
    return map { data ->
        GifImage(
            id = data.id,
            title = data.title,
            urlOriginal = data.images.original?.url,
            urlPreview = data.images.previewGif?.url,
        )
    }
}