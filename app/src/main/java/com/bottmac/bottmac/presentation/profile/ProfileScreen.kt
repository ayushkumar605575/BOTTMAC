package com.bottmac.bottmac.presentation.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bottmac.bottmac.R
import com.bottmac.bottmac.email_sign_in_service.SignedInUser

@Composable
fun ProfileScreen(
    modifier: Modifier,
    userType: (Int) -> Unit
) {
    val cSignedInUser : SignedInUser = hiltViewModel()
    val userData = cSignedInUser.signedInUserData.collectAsState().value
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
                userType(1)
            }) {
                Text(text = "SIGN IN")
            }
        }
    }
    else {
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
            HorizontalDivider()
            LazyColumn {
                item { Options(heading = "My Orders", subHeading = "1") }
                item { Options(heading = "Shipping Address", subHeading = "1") }
                item { Options(heading = "My reviews", subHeading = "1") }
                item { Options(heading = "Edit Profile", subHeading = "1") }
                item { HorizontalDivider() }
            }
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            ElevatedButton(onClick = {
                cSignedInUser.signOutCurrentUser()
                userType(1)
            }) {
                Text(text = "Sign OUT")
            }
        }
    }
}


@Composable
fun Options(
    heading: String,
    subHeading: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
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
