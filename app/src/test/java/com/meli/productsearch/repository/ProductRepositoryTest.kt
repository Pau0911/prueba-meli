package com.meli.productsearch.repository

import com.meli.productsearch.model.*
import com.meli.productsearch.network.MeliAPI
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class ProductRepositoryTest {

    private val api = mock<MeliAPI>()

    @RelaxedMockK
    private lateinit var repository: ProductRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun validateGetResultsDiferenteDeVacio() = runBlocking {
        val list = ApiResponse("MCO", "casa", paging, product, true)
        coEvery {
            repository.getResponse("MCO", "casa", 1)
        } returns list

        assert(list == repository.getResponse("MCO", "casa", 1))

    }

    @Test
    fun testApisucceeds() = runBlocking {
        whenever(api.getProduct("MCO991847965")).thenReturn(productDetail)
        repository.getDetailProduct("MCO991847965").collect {
            assertEquals(product, it)
        }
    }

    @Test
    fun ifApiFailedThenReturnNull() = runBlocking {
        val error = NullPointerException("null pointer")
        whenever(api.getProduct("MCO991847965")).thenThrow(error)
        repository.getDetailProduct("MCO991847965").catch {
            assertEquals("null pointer", it.message)
        }.collect { assertNull(it) }
    }
}
