package com.bottmac.bottmac.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bottmac.bottmac.R
import com.bottmac.bottmac.navigation.BottomBar
import com.bottmac.bottmac.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenStructure(
    navController: NavHostController,
    onSearchActive: () -> Unit,
    screen: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp,
//                            letterSpacing = 2.sp,
//                            fontFamily = FontFamily.SansSerif,
//                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary

                ),
                navigationIcon = {

                },
                actions = {
                    if (navController.currentDestination?.route == NavigationRoutes.Home.route) {
                        IconButton(onClick = onSearchActive) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navController) },
        content = screen
    )
}