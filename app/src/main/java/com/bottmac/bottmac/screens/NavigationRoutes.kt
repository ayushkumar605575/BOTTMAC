package com.bottmac.bottmac.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object SignIn : NavigationRoutes(
        "signIn",
        "Sign In",
        Icons.AutoMirrored.Filled.Login
    )

    data object SignUp : NavigationRoutes(
        "signUp",
        "Sign Up",
        Icons.Default.AccountBox
    )
    data object Home : NavigationRoutes(
        "home",
        "Home",
        Icons.Default.Home
    )

    data object Cart : NavigationRoutes(
        "cart",
        "Cart",
        Icons.Default.ShoppingCart
    )

    data object Order : NavigationRoutes(
        "order",
        "Order",
        Icons.Default.Money
    )

    data object Fav : NavigationRoutes(
        "wishList",
        "Wish List",
        Icons.Default.Checklist
    )

    data object Search : NavigationRoutes(
        "search",
        "Search",
        Icons.Default.Search
    )

    data object Profile : NavigationRoutes(
        "profile",
        "Profile",
        Icons.Default.Person
    )
}
