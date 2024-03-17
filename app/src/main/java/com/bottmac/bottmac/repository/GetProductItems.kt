package com.bottmac.bottmac.repository

import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.models.ProductItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetProductItems @Inject constructor(private val productsApi: ProductsApi) {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products: StateFlow<List<ProductItem>>
        get() = _products

    suspend fun getProducts() {
        val response = productsApi.getProducts()
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
        println(response.isSuccessful)
        if (response.isSuccessful) {
            _products.emit(response.body() ?: emptyList())
        } else {
            _products.emit(emptyList())
        }
    }
}
