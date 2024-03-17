package com.bottmac.bottmac.api

import com.bottmac.bottmac.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ProductsApi {
// @Header("Authorization") String authorization
    @GET("/?format=json")
    suspend fun getProducts(): Response<List<ProductItem>>

//    @GET("/?format=json")
//    suspend fun getCategories(): Response<List<String>>
}