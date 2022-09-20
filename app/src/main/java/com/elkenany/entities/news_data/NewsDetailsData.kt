package com.elkenany.entities.news_data

import com.squareup.moshi.Json

data class NewsDetailsData(
    val banners: List<Any?>,
    val logos: List<Any?>,
    val id: Long,
    val title: String?,
    val image: String?,
    val desc: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "news")
    val news: List<NewsDaum?>,
)
