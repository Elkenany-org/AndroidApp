package com.elkenany.entities.ships

data class ShipsStatisticsData(
    val ships: List<ShipDaum?>,
    val products: List<Product?>,
    val countries: List<Country?>,
)

data class ShipDaum(
    val id: Long?,
    val product: String?,
    val country: String?,
    val load: Long?,
)

data class Product(
    val id: Long?,
    val name: String?,
    val load: Long?,
)

data class Country(
    val country: String?,
)