package com.bottmac.bottmac.api

import com.bottmac.bottmac.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("/?format=json")
    suspend fun getProducts(): Response<List<ProductItem>>

//    @GET("/?format=json")
//    suspend fun getCategories(): Response<List<String>>
}