package com.elkenany.entities.guide_magazine

import com.squareup.moshi.Json

data class MagazineDetailsData(
    val id: Long?,
    val name: String?,
    @Json(name = "short_desc")
    val shortDesc: String?,
    val about: String?,
    val address: String?,
    val latitude: String?,
    val longitude: String?,
    val rate: Double,
    @Json(name = "count_rate")
    val countRate: Long?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val phones: List<MagazineItemPhones?>,
    val emails: List<MagazineItemEmail?>,
    val mobiles: List<MagazineItemMobile?>,
    val faxs: List<MagazineItemFax?>,
    val social: List<MagazineItemSocial?>,
    val addresses: List<MagazineItemAddress?>?,
    val gallary: List<MagazineItemGallery?>,
    val guides: List<MagazineItemGuide?>,
)

data class MagazineItemEmail(
    val email: String?,
)

data class MagazineItemPhones(
    val phones: String?,
)

data class MagazineItemMobile(
    val mobile: String?,
)

data class MagazineItemFax(
    val fax: String?,
)

data class MagazineItemSocial(
    @Json(name = "social_id")
    val socialId: Long?,
    @Json(name = "social_link")
    val socialLink: String?,
    @Json(name = "social_name")
    val socialName: String?,
    @Json(name = "social_icon")
    val socialIcon: String?,
)

data class MagazineItemAddress(
    val address: String?,
    val latitude: String?,
    val longitude: String?,
)

data class MagazineItemGallery(
    val image: String?,
    val id: Long?,
    val link: String?,
)

data class MagazineItemGuide(
    val link: String?,
    val name: String?,
    val image: String?,
)
