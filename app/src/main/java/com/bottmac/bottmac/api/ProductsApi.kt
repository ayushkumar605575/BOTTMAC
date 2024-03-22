package com.bottmac.bottmac.api

import com.bottmac.bottmac.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductsApi {
// @Header("Authorization") String authorization
    @GET("/?format=json")
    suspend fun getProducts(@Header("auth") authorization: String): Response<List<ProductItem>>
}