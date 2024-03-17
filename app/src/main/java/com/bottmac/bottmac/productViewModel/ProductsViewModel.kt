package com.bottmac.bottmac.productViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.repository.GetProductItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getProductItems: GetProductItems): ViewModel() {

    val productItems: StateFlow<List<ProductItem>>
        get() = getProductItems.products

    init {
        viewModelScope.launch {
            getProductItems.getProducts()
        }
    }
}