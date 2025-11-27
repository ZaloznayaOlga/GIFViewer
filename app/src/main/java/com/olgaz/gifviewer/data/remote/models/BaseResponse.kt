package com.olgaz.gifviewer.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse (
    @SerialName("data")
    val responseData: List<DataDto>?,
    @SerialName("pagination")
    val pagination: PaginationDto?,
    @SerialName("meta")
    val meta: MetaDto
)

@Serializable
data class PaginationDto(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("count")
    val count: Int,
    @SerialName("offset")
    val offset: Int
)

@Serializable
data class MetaDto(
    @SerialName("status")
    val status: Int,
    @SerialName("msg")
    val msg: String,
    @SerialName("response_id")
    val responseId: String
)