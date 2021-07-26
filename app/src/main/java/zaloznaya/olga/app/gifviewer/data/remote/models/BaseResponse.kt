package zaloznaya.olga.app.gifviewer.data.remote.models

import com.google.gson.annotations.SerializedName

data class BaseResponse (

    @SerializedName("data")
    val responseData: List<DataDto>?,
    @SerializedName("pagination")
    val pagination: PaginationDto?,
    @SerializedName("meta")
    val meta: MetaDto
)

data class PaginationDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("offset")
    val offset: Int
)

data class MetaDto(
    @SerializedName("status")
    val status: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("response_id")
    val responseId: String
)