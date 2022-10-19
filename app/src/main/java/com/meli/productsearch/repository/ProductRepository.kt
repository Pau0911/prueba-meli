package com.meli.productsearch.repository


import com.meli.productsearch.model.ApiResponse
import com.meli.productsearch.model.ProductDetails
import com.meli.productsearch.network.MeliAPI
import com.meli.productsearch.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val meliAPI: MeliAPI
) {
    suspend fun getResponse(
        siteId: String,
        searchText: String,
        pageId: Int
    ): ApiResponse {
        return meliAPI.getResponse(siteId, searchText, pageId)
    }


    fun getDetailProduct(id: String): Flow<Response<ProductDetails>> =
        flow {
            try {
                emit(Response.Loading)
                val responseApi = meliAPI.getProduct(id)
                emit(Response.Success(responseApi))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
}