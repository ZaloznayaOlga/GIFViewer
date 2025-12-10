package com.olgaz.gifviewer.domain.model

data class GifImage(
    val id: String,
    val title: String,
    val urlPreview: String?,
    val urlOriginal: String?
)