package com.abhi165.assignment.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubscribeUnconfirmedTransaction (
    @Json(name = "op")
    val subscribeTo:String = "unconfirmed_sub"
        )
