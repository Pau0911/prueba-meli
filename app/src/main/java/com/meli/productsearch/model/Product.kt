package com.meli.productsearch.model

data class Product(
    val address: Address,
    val attributes: List<Attribute>,
    val condition: String,
    val currency_id: String,
    val id: String,
    val permalink: String,
    val price: Long,
    val seller: Seller,
    val seller_address: SellerAddress,
    val site_id: String,
    val thumbnail: String,
    val title: String
)

data class Address(
    val city_id: String,
    val city_name: String,
    val state_id: String,
    val state_name: String
)


data class Seller(
    val car_dealer: Boolean,
    val id: Int,
    val power_seller_status: String?,
    val real_estate_agency: Boolean,
)

data class SellerAddress(
    val city: City,
    val country: Country,
    val latitude: String,
    val longitude: String,
)

data class City(
    val id: String,
    val name: String
)

data class Country(
    val id: String,
    val name: String
)

