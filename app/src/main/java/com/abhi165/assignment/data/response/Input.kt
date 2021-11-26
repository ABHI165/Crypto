package com.abhi165.assignment.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Input(
    @Json(name = "prev_out")
    val prevOut: PrevOut,
    @Json(name = "script")
    val script: String,
    @Json(name = "sequence")
    val sequence: Long
)