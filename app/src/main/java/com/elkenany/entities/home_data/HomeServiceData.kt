package com.elkenany.entities.home_data

import com.squareup.moshi.Json

data class HomeServiceData(

    @Json(name = "logos")
    val serviceLogos: List<ServiceLogo>?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "recomandtion")
    val serviceRecommendation: List<ServiceRecomandtion>?,
    @Json(name = "show")
    val serviceShows: List<ServiceShow>?,
    @Json(name = "magazine")
    val serviceMagazine: List<ServiceMagazine>?,
)

data class HomeServiceDaum(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
)

data class ServiceLogo(
    val id: Long?,
    val link: String?,
    val image: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
)

data class ServiceRecomandtion(
    val id: Long?,
    val name: String?,
    val image: String?,
    val type: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "image_thum_url")
    val imageThumUrl: String?,
)

data class ServiceShow(
    val id: Long?,
    val name: String?,
    val image: String?,
    val type: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "image_thum_url")
    val imageThumUrl: String?,
)

data class ServiceMagazine(
    val id: Long?,
    val name: String?,
    val image: String?,
    val type: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "image_thum_url")
    val imageThumUrl: String?,
)