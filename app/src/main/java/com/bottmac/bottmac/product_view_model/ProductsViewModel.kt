package com.bottmac.bottmac.product_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bottmac.bottmac.models.NewUser
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.repository.GetProductItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getProductItems: GetProductItems): ViewModel() {

    val productItems: StateFlow<List<ProductItem>>
        get() = getProductItems.products

//    val createUser = getProductItems.createUser(NewUser("gfy785r657","A",LocalDateTime.now(),LocalDateTime.now()))

    init {
        viewModelScope.launch {
            getProductItems.createUser(NewUser("gfy785r657","A",LocalDateTime.now(),LocalDateTime.now()))
            getProductItems.getProducts()
        }
    }
}