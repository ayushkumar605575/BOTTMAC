package com.bottmac.bottmac.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bottmac.bottmac.google_sign_in_service.UserData
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.presentation.product_details.ProductScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    products: List<ProductItem>,
    userData: UserData,
    isSearchActive: Boolean,
    mainNavController: NavController,
    primaryNavHostController: NavHostController
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
    LaunchedEffect(key1 = products.size) {
        queryProductItems = products
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedContent(targetState = isSearchActive, label = "") { active ->
            if (active) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { queryText ->
                        isActive = !isActive
//                        println(78)
                        queryProductItems = products.filter {
                            queryText.lowercase() in it.productName.lowercase()
                        }
                        println(queryProductItems)
                    },
                    active = isActive,
                    onActiveChange = {
                        isActive = it
                    },
                    trailingIcon = {
                        AnimatedVisibility(isActive) {
                            IconButton(onClick = {
                                query = ""
                            }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    },
                    leadingIcon = {
                        AnimatedContent(
                            targetState = isActive,
                            label = "LeadingIconAnimation"
                        ) { active ->
                            if (active) {
                                IconButton(onClick = {
                                    isActive = false
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    },
                    shadowElevation = 42.dp,
                    placeholder = { Text(text = "Search Products") }
                ) {
                    Text(text = "My Searches")
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
            } else {
                query = ""
                queryProductItems = products
            }
        }
        ProductScreen(
            modifier = Modifier,
            products = queryProductItems,
            userData = userData,
            mainNavController = mainNavController,
            primaryNavHostController = primaryNavHostController
        )
    }
}
