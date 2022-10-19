package com.meli.productsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.meli.productsearch.model.Product
import com.meli.productsearch.repository.ProductRepository
import com.meli.productsearch.repository.paged.ProductSource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :
    ViewModel() {

    val DEFAULT_SITE_ID: String = "MCO"


    fun getResults(searchText: String): Flow<PagingData<Product>> {
        val products: Flow<PagingData<Product>> = Pager(PagingConfig(pageSize = 20)) {
            ProductSource(
                productRepository,
                searchText,
                DEFAULT_SITE_ID
            )
        }.flow
            .cachedIn(viewModelScope)
        return products
    }

}