package com.bottmac.bottmac.models

import java.util.Objects

data class ProductItem(
    val productId: Int,
    val productImage: List<String>,
    val productName: String
)