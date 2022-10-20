package com.meli.productsearch.repository

import com.meli.productsearch.model.*

val paging = Paging(37155, 1000, 1, 1)
val adress = Address("TUNPUENVTmE3NmQ4", "Chía", "TUNPQ0NISTk4OWIx", "Cundinamarca")
val attributesList = listOf(
    Attribute(
        "DFLT", "Otros", "ITEM_CONDITION", "Condición del ítem", 1,
        "2230284", "Nuevo"
    )
)
val country = Country("CO", "Colombia")
val city = City("TUNPQ01FRGRjNjc4", "Medellín")
val sellerAddress = SellerAddress(city, country, "", "")
val tags = listOf(Any())
val seller = Seller(false, 600185355, null, false)
val product = listOf(
    Product(
        adress,
        attributesList,
        "new",
        "classified",
        "MCO991847965",
        "http://perfil.mercadolibre.com.co/INMOBILIARIA+GLORIAE",
        2300000000,
        seller, sellerAddress,
        "MCO",
        "http://http2.mlstatic.com/D_630325-MCO51957512487_102022-I.jpg",
        "Casa Campestre En San Jeronimo"
    )
)