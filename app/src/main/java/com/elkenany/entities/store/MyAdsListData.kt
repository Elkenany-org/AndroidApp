package com.elkenany.entities.store

import com.squareup.moshi.Json

data class MyAdsListData(
    val banners: List<Any?>,
    val logos: List<Any?>,
    val data: List<MyAdsDaum?>,
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

data class MyAdsDaum(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val address: String?,
    val desc: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)