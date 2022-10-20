package com.meli.productsearch.model

data class ProductDetails(
    val accepts_mercadopago: Boolean,
    val attributes: List<Attribute>,
    val condition: String,
    val id: String,
    val permalink: String,
    val pictures: List<Picture>,
    val price: Long,
    val seller_address: SellerAddress,
    val seller_contact: Any,
    val seller_id: Int,
    val site_id: String,
    val thumbnail: String,
    val title: String,
)

data class Picture(
    val id: String,
    val max_size: String,
    val quality: String,
    val secure_url: String,
    val size: String,
    val url: String
)




