package com.bottmac.bottmac.repository

import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.models.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class GetProductItems @Inject constructor(private val productsApi: ProductsApi) {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products: StateFlow<List<ProductItem>>
        get() = _products

    suspend fun getProducts() {
        val response: Response<List<ProductItem>> = productsApi.getProducts()// ?: null //} catch (_: Exception) {
            //emptyList<ProductItem>()
//            Response<List<ProductItem>>("","","",)
//            response = Response()
//        }
        println(response.isSuccessful)
        if (response.isSuccessful) {
            _products.emit(response.body() ?: emptyList())
        }
    }
}