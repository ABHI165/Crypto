package com.abhi165.assignment.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "op")
    val op: String,
   @Json(name = "x")
   val x: X
)