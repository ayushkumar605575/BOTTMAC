package com.bottmac.bottmac.api

import com.bottmac.bottmac.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET
//import retrofit2.http.Header

interface ProductsApi {

    @GET("/")
    suspend fun getProducts(): Response<List<ProductItem>>

    @GET("/")
    suspend fun getCategories(): Response<List<String>>
}