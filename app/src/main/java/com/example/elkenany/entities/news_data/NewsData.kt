package com.example.elkenany.entities.news_data

import com.squareup.moshi.Json

data class NewsData(
    @Json(name = "sections")
    val sections: List<NewsSection?>,
    @Json(name = "banners")
    val banners: List<NewBanners?>,
    @Json(name = "logos")
    val logos: List<NewsLogos?>,
    @Json(name = "data")
    val data: List<NewsDaum?>,
    @Json(name = "current_page")
    val currentPage: Long?,
    @Json(name = "last_page")
    val lastPage: Long?,
    @Json(name = "first_page_url")
    val firstPageUrl: String?,
    @Json(name = "next_page_url")
    val nextPageUrl: String?,
    @Json(name = "last_page_url")
    val lastPageUrl: String?,
)

data class NewsSection(
    val id: Long?,
    val name: String?,
    val type: String?,
    val selected: Long?,
)

data class NewsDaum(
    val id: Long?,
    val title: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)

data class NewBanners(
    val id: Long?,
    val link: String?,
    val image: String?,
)

data class NewsLogos(
    val id: Long?,
    val link: String?,
    val image: String?,
)
