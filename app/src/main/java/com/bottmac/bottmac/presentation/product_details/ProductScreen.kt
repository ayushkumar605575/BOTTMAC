package com.bottmac.bottmac.presentation.product_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bottmac.bottmac.productViewModel.ProductsViewModel

@Composable
fun ProductScreen(modifier: Modifier) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val products = productsViewModel.productItems.collectAsState().value
    if (products.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(products) { product ->
                ProductCard(
                    productName = product.productName,
                    productsFeatures = product.productFeatures.split("\\n"),
                    productsImages = product.productImage
                )
            }
        }
    } else {
        Column {
            Text(text = "Unable to Fetch data")
        }
    }
}