package com.bottmac.bottmac.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object SignIn : NavigationRoutes(
        "signIn",
        "Sign In",
        Icons.AutoMirrored.Filled.Login,
        Icons.AutoMirrored.Outlined.Login
    )

    data object SignUp : NavigationRoutes(
        "signUp",
        "Sign Up",
        Icons.Default.AccountBox,
        Icons.Outlined.AccountBox
    )

    data object Home : NavigationRoutes(
        "home",
        "Home",
        Icons.Default.Home,
        Icons.Outlined.Home
    )

//    data object Cart : NavigationRoutes(
//        "cart",
//        "Cart",
//        Icons.Default.ShoppingCart,
//        Icons.Outlined.ShoppingCart
//    )

//    data object Order : NavigationRoutes(
//        "order",
//        "Order",
//        Icons.Default.CheckBox,
//        Icons.Outlined.CheckBox
//    )

//    data object Fav : NavigationRoutes(
//        "wishList",
//        "Wish List",
//        Icons.Default.Checklist,
//        Icons.Outlined.Checklist
//    )

//    data object Search : NavigationRoutes(
//        "search",
//        "Search",
//        Icons.Default.Search,
//        Icons.Outlined.Search
//    )

    data object Profile : NavigationRoutes(
        "profile",
        "Profile",
        Icons.Default.Person,
        Icons.Outlined.Person
    )
}
