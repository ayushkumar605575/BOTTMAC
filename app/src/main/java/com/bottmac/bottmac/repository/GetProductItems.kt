package com.bottmac.bottmac.repository

import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.firebase_service.FireBaseService
import com.bottmac.bottmac.models.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetProductItems @Inject constructor(private val productsApi: ProductsApi) {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products: StateFlow<List<ProductItem>>
        get() = _products

    private val auth = FireBaseService()

    suspend fun getProducts() {
        try {
            val response = productsApi.getProducts(authorization = auth.getTokenId())
            println(response.isSuccessful)
            if (response.isSuccessful) {
                _products.emit(response.body() ?: emptyList())
            } else {
                _products.emit(emptyList())
            }
        } catch (e: Exception) {
                _products.emit(listOf(ProductItem("", -1, listOf(""), "")))
            println("Error fetching data")
        }
//        println(response.)
//            .enqueue(
//            object:Callback<List<ProductItem>>{
//                override fun onResponse(
//                    call: Call<List<ProductItem>>,
//                    response: Response<List<ProductItem>>
//                ) {
//                    if (response.isSuccessful && response.body() !=null) {
//                        _products.emit(response.body()!!)
//                    }
//                }
//
//                override fun onFailure(call: Call<List<ProductItem>>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//            }
//        )

    }
}
