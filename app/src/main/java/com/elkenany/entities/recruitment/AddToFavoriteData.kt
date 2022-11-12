package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class AddToFavoriteData(
    @Json(name = "favorite_detials")
    val favoriteDetials: FavoriteDetials?,
)

data class FavoriteDetials(
    val id: Long?,
    val job: String?,
    val customer: String?,
)