package com.abhi165.assignment.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrevOut(
    @Json(name = "addr")
    val addr: String,
    @Json(name = "n")
    val n: Int,
    @Json(name = "script")
    val script: String,
    @Json(name = "spent")
    val spent: Boolean,
    @Json(name = "tx_index")
    val txIndex: Int,
    @Json(name = "type")
    val type: Int,
    @Json(name = "value")
    val value: Int
)