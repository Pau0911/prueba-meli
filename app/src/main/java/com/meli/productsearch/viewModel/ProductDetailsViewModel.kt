package com.meli.productsearch.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.productsearch.model.ProductDetails
import com.meli.productsearch.repository.ProductRepository
import com.meli.productsearch.utils.Response

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :
    ViewModel() {

    private val _productState = mutableStateOf<Response<ProductDetails>>(Response.Success(null))
    val productState: State<Response<ProductDetails>> = _productState

    fun getDetailProduct(id: String) {
        viewModelScope.launch {
            productRepository.getDetailProduct(id).collect { response ->
                _productState.value = response
            }
        }
    }

}