package com.bottmac.bottmac.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bottmac.bottmac.R
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.google_sign_in_service.UserData
import com.bottmac.bottmac.navigation.NavigationRoutes

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreenStructure(
//    navController: NavHostController,
//    userData: UserData,
//    cSignedInUser: SignedInUser,
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.logo),
//                            contentDescription = null,
//                            modifier = Modifier.size(40.dp),
//                        )
//                        Text(
//                            text = stringResource(id = R.string.app_name),
//                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                            fontSize = 32.sp,
//                            fontFamily = FontFamily.SansSerif,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    MaterialTheme.colorScheme.secondaryContainer
//                ),
//                navigationIcon = {
//
//                },
//                actions = {
////                    IconButton(onClick = {
////                        navController.navigate(NavigationRoutes.Home.route) {
////                            popUpTo(navController.graph.findStartDestination().id)
////                            launchSingleTop = true
////                        }
////                        isSearchActive = !isSearchActive
////                    }) {
////                        Icon(
////                            Icons.Default.Search,
////                            contentDescription = "Search",
////                        )
////                    }
//                }
//            )
//        },
//        bottomBar = { BottomBar(navController = navController) }
//    ) { paddingValues ->
//        println(navController.currentDestination?.route)
//        ProfileScreen(
//            modifier = Modifier.padding(paddingValues),
//            userData = userData,
//            cSignedInUser = cSignedInUser,
//            navController = navController
//        )
//
//    }
//}


@Composable
fun ProfileScreen(
    modifier: Modifier,
    userData: UserData,
    cSignedInUser: SignedInUser,
    navController: NavHostController,
) {
    val profileOptions = listOf(
        ProfileOptions.MyOrders,
        ProfileOptions.ShippingAddress,
        ProfileOptions.EditProfile,
    )
    println(userData)
    if (userData.userId == null) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign In with your account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            ElevatedButton(onClick = {
                navController.navigate(NavigationRoutes.SignIn.route) {
                    popUpTo("main") {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "SIGN IN")
            }
        }
    } else {
        Column(
            modifier = modifier
        )
        {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp),
                    model = userData.profilePicUrl?.ifEmpty { R.drawable.profile_placeholder },
                    placeholder = painterResource(id = R.drawable.profile_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Picture"
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (userData.userName != null) {
                        Text(
                            text = userData.userName,
                            fontSize = 32.sp,
                        )
                    }
                    if (userData.phoneNumber != null) {
                        Text(
                            text = userData.phoneNumber
                        )
                    }
                    if (userData.email != null) {
                        Text(
                            text = userData.email

                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(44.dp))
            LazyColumn {
                item { HorizontalDivider() }
                items(profileOptions) { profileOption ->
                    ProfileOptionsComposable(
                        heading = profileOption.title,
                        subHeading = profileOption.subTitle,
                        route = profileOption.routes,
                        navController = navController
                    )
                }
            }
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            ElevatedButton(onClick = {
                cSignedInUser.signOutCurrentUser()
                navController.navigate(NavigationRoutes.SignIn.route) {
                    popUpTo("main") {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}


@Composable
fun ProfileOptionsComposable(
    heading: String,
    subHeading: String,
    route: String,
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = heading, fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = subHeading)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
    HorizontalDivider()
}
