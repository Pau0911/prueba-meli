package com.meli.productsearch.network

import com.meli.productsearch.model.ApiResponse
import com.meli.productsearch.model.ProductDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MeliAPI {

    @GET("/sites/{site_id}/search")
    suspend fun getResponse(
        @Path("site_id") site_id: String,
        @Query("q") q: String,
        @Query("offset") offset: Int
    ): ApiResponse

    @GET("/items/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductDetails


}