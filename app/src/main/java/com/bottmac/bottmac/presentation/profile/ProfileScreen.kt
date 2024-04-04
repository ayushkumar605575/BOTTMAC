package com.bottmac.bottmac.presentation.profile

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.bottmac.bottmac.R
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.google_sign_in_service.UserData
import com.bottmac.bottmac.navigation.NavigationRoutes

@Composable
fun ProfileScreen(
    modifier: Modifier,
    userData: UserData,
    cSignedInUser: SignedInUser,
    primaryNavHostController: NavHostController
) {
    var isProfilePicLoading by rememberSaveable {
        mutableStateOf(true)
    }
    val profileOptions = remember {
        mutableStateListOf(
            ProfileOptions.MyOrders,
            ProfileOptions.ShippingAddress,
            ProfileOptions.EditProfile,
        )
    }
    println(userData)
    AnimatedContent(
        targetState = userData.userName == null,
        label = ""
    ) { isUser ->
        if (isUser) {
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
                    primaryNavHostController.navigate(NavigationRoutes.SignIn.route) {
                        popUpTo("main") {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
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
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .decoderFactory(
                                    if (SDK_INT >= 28) {
                                        ImageDecoderDecoder.Factory()
                                    } else {
                                        GifDecoder.Factory()
                                    }
                                )
                                .data(userData.profilePicUrl)
                                .scale(Scale.FILL)
                                .size(Size.ORIGINAL)
                                .placeholder(R.drawable.profile_placeholder)
                                .crossfade(true)
                                .build(),
                            onSuccess = {
                                println(it)
                                isProfilePicLoading = false
                            },
                            placeholder = painterResource(R.drawable.profile_placeholder),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Profile Picture"
                        )
                        if (isProfilePicLoading) {
                            CircularProgressIndicator(strokeCap = StrokeCap.Round)
                        }
                    }
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
                    items(
                        items = profileOptions
                    ) { profileOption ->
                        ProfileOptionsComposable(
                            heading = profileOption.title,
                            subHeading = profileOption.subTitle,
                            route = profileOption.routes,
                            primaryNavHostController = primaryNavHostController
                        )
                    }
                }
            }
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ElevatedButton(
                    modifier = Modifier.padding(bottom = 8.dp),
                    onClick = {
                        cSignedInUser.signOutCurrentUser()
                        primaryNavHostController.navigate(route = NavigationRoutes.SignIn.route) {
                            popUpTo("startUp") {
                                inclusive = true
                            }
                        }
                    }) {
                    Text(text = "Sign Out")
                }
            }
        }
    }
}


@Composable
fun ProfileOptionsComposable(
    heading: String,
    subHeading: String,
    route: String,
    primaryNavHostController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                primaryNavHostController.navigate(route)
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
