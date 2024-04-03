package com.bottmac.bottmac.presentation.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.firebase_service.FireBaseService
import com.bottmac.bottmac.google_sign_in_service.UserData

@Composable
fun EditProfileScreen(
    modifier: Modifier,
    userData: UserData,
    cSignedInUser: SignedInUser
) {
    val context = LocalContext.current
    var isImageUploading by rememberSaveable {
        mutableStateOf(false)
    }
    val firebaseServices = FireBaseService()
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                isImageUploading = true
                firebaseServices.uploadImage(uri) {
                    isImageUploading = it
                    cSignedInUser.getUserUpdatedData()
                }
            }
        }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userData.profilePicUrl)
                    .scale(Scale.FILL)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .build(),
                alpha = if (isImageUploading) 0.5f else 1f,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            if (isImageUploading) {
                CircularProgressIndicator()
            }
        }
    }
}
