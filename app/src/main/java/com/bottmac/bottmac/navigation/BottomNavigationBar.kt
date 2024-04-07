package com.bottmac.bottmac.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@Composable
fun BottomBar(navController: NavHostController) {
    val bottomNavigationRoutes = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Profile,
    )
    var selectedScreenInd by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        bottomNavigationRoutes.forEachIndexed { index, bottomNavigationRoute ->
            AddItem(
                index = index,
                selectedScreenInd = selectedScreenInd,
                onSelection = { selectedScreenInd = index },
                bottomNavigationRoute = bottomNavigationRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    index: Int,
    selectedScreenInd: Int,
    onSelection: () -> Unit,
    bottomNavigationRoute: NavigationRoutes,
    navController: NavHostController,
) {
    NavigationBarItem(
        selected = selectedScreenInd == index,
        alwaysShowLabel = selectedScreenInd == index,
        label = {
            Text(
                bottomNavigationRoute.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = {
            navController.navigate(bottomNavigationRoute.route) {
                popUpTo("startUp") {
                    saveState = true
                    inclusive = true
                }
                restoreState = true
                launchSingleTop = true
            }
            onSelection()
        },
        icon = {
            Icon(
                imageVector = if (selectedScreenInd == index) bottomNavigationRoute.selectedIcon else bottomNavigationRoute.unselectedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        colors = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.surface,
            unselectedTextColor = MaterialTheme.colorScheme.surface,
            disabledIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
