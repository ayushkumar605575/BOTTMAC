package com.bottmac.bottmac.presentation.product_search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(modifier: Modifier) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ){
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                isActive = !isActive
            },
            active = isActive,
            onActiveChange = { isActive = !isActive }
        ) {
            Text(text = "My Searches")
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun SearchPrev() {
   SearchScreen(modifier = Modifier)
}