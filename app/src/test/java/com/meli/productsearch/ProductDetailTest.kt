package com.meli.productsearch.model

val picture = listOf(
    Picture(
        "630325-MCO51957512487_102022",
        "1080x607",
        "",
        "https://http2.mlstatic.com/D_630325-MCO51957512487_102022-O.jpg",
        "500x281",
        "http://http2.mlstatic.com/D_630325-MCO51957512487_102022-O.jpg"
    )
)
val attributesList = listOf(
    Attribute(
        "DFLT", "Otros", "BEDROOMS", "Habitaciones", 1,
        null, "0"
    )
)
val country = Country("CO", "Colombia")
val city = City("TUNPQ01FRGRjNjc4", "Medellín")
val sellerAddress = SellerAddress(city, country, "", "")

val productDetail = ProductDetails(
    true,
    attributesList,
    "new",
    "MCO991847965",
    "https://casa.mercadolibre.com.co/MCO-991847965-casa-campestre-en-san-jeronimo-_JM",
    picture,
    2300000000,
    sellerAddress,
    "",
    600185355,
    "MCO",
    "http://http2.mlstatic.com/D_630325-MCO51957512487_102022-I.jpg",
    "Casa Campestre En San Jeronimo"
)
