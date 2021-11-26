package com.abhi165.assignment.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class X(
    @Json(name = "hash")
    val hash: String,
    @Json(name = "inputs")
    val inputs: List<Input>,
    @Json(name = "lock_time")
    val lockTime: Int,
    @Json(name = "out")
    val `out`: List<Out>,
    @Json(name = "relayed_by")
    val relayedBy: String,
    @Json(name = "size")
    val size: Int,
    @Json(name = "time")
    val time: Int,
    @Json(name = "tx_index")
    val txIndex: Int,
    @Json(name = "ver")
    val ver: Int,
    @Json(name = "vin_sz")
    val vinSz: Int,
    @Json(name = "vout_sz")
    val voutSz: Int
)