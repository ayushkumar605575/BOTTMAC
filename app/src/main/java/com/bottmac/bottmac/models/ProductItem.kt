package com.bottmac.bottmac.models

data class ProductItem(
    val productFeatures: String,
    val productId: Int,
    val productImage: List<String>,
    val productName: String
)