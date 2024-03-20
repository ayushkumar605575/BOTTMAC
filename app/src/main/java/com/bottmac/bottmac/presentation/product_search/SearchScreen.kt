package com.bottmac.bottmac.presentation.product_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.google_sign_in_service.UserData
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.presentation.product_details.ProductScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    products: List<ProductItem>,
    userData: UserData,
    userType: (Int) -> Unit,
    isSearchActive: Boolean
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    var queryProductItems by rememberSaveable {
        mutableStateOf(products)
    }
    var productDetailView by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = products.size) {
        queryProductItems = products
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isSearchActive) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { queryText ->
                    isActive = !isActive
                    println(78)
                    queryProductItems = products.filter {
                        queryText.lowercase() in it.productName.lowercase()//, it.productFeatures.lowercase())
                    }
                    println(queryProductItems)
                },
                active = isActive,
                onActiveChange = {
                    isActive = it
                    productDetailView = false
                },
                trailingIcon = {
                    if (isActive) {
                        IconButton(onClick = {
                            query = ""
                            isActive = false
                        }) {
                            Icon(imageVector = Icons.Default.SearchOff, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
//                            isActive = true
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    }
                },
                leadingIcon = {

                },
                shadowElevation = 42.dp,
                placeholder = { Text(text = "Search Here") }
            ) {
                Text(text = "My Searches")
            }
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()
        }
        ProductScreen(
            modifier = Modifier,
            products = queryProductItems,
            userData = userData,
            productDetails = productDetailView,
            onProductDetails = {
                productDetailView = !productDetailView
            }) {
            userType(it)
        }


    }
}
