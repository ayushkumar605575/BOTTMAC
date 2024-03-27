package com.bottmac.bottmac.models

import androidx.compose.runtime.Immutable
@Immutable
data class ProductItem(
    val productFeatures: String,
    val productId: Int,
    val productImage : List<String>,
    val productName: String
)