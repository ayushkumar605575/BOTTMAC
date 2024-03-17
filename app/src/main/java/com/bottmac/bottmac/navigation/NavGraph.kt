package com.bottmac.bottmac.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bottmac.bottmac.presentation.profile.ProfileScreen
import com.bottmac.bottmac.presentation.product_details.ProductScreen
import com.bottmac.bottmac.screens.CartScreen
import com.bottmac.bottmac.screens.FavScreen
import com.bottmac.bottmac.screens.OrderScreen
import com.bottmac.bottmac.presentation.product_search.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userType: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(route = NavigationRoutes.Home.route) {
            ProductScreen(modifier = Modifier.padding(paddingValues), userType = userType)
        }
        composable(route = NavigationRoutes.Cart.route) {
            CartScreen(modifier = Modifier.padding(paddingValues), navController)
        }
        composable(route = NavigationRoutes.Order.route) {
            OrderScreen(modifier = Modifier.padding(paddingValues), navController)
        }
        composable(route = NavigationRoutes.Fav.route) {
            FavScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = NavigationRoutes.Search.route) {
            SearchScreen(Modifier.padding(paddingValues))
        }
        composable(route = NavigationRoutes.Profile.route) {
            ProfileScreen(
                userType = userType,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Cart,
        NavigationRoutes.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationRoutes,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination?.route == screen.route,
        alwaysShowLabel = currentDestination?.route == screen.route,
        label = { Text(screen.title, color = MaterialTheme.colorScheme.primary) },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}
