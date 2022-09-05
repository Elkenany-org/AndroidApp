package com.example.elkenany.entities.guide

import com.squareup.moshi.Json

data class CompanyDetailsData(
    val id: Long?,
    val name: String?,
    @Json(name = "short_desc")
    val shortDesc: String?,
    val about: String?,
    val address: String?,
    val latitude: String?,
    val longitude: String?,
    val rate: Float?,
    @Json(name = "count_rate")
    val countRate: Long?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val phones: List<Phone?>,
    val emails: List<Email?>,
    val mobiles: List<Any?>,
    val faxs: List<Fax?>,
    val social: List<Social?>,
    val addresses: List<Address?>,
    val gallary: List<Gallary?>,
    val products: List<Any?>,
    val localstock: List<Localstock?>,
    val fodderstock: List<Localstock?>,
    val transports: List<Transports?>,
    val cities: List<City?>,
)

data class Phone(
    val phone: String?,
)

data class Email(
    val email: String?,
)

data class Social(
    @Json(name = "social_id")
    val socialId: Long?,
    @Json(name = "social_link")
    val socialLink: String?,
    @Json(name = "social_name")
    val socialName: String?,
    @Json(name = "social_icon")
    val socialIcon: String?,
)

data class Address(
    val address: String?,
    val latitude: String?,
    val longitude: String?,
)

data class Localstock(
    val image: String?,
    val name: String?,
    val id: Long?,
)

data class City(
    val id: Long?,
    val name: String?,
)

data class Fax(
    val id: Long?,
    val name: String?,
)

data class Gallary(
    val id: Long?,
    val name: String?,
    val image: String?,
)

data class Transports(
    val id: Long?,
    val name: String?,
    val price: Float?,
    val type: String?,
    val city: String?,
)
