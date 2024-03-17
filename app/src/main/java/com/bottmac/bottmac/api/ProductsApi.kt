package com.bottmac.bottmac.api

import com.bottmac.bottmac.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
// @Header("Authorization") String authorization
    @GET("/?format=json")
    suspend fun getProducts(): Response<List<ProductItem>>

}