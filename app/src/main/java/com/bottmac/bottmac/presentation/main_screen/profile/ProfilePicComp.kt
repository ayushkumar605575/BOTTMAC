package com.bottmac.bottmac.presentation.main_screen.profile

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.bottmac.bottmac.R

@Composable
fun ProfilePicComp(
    imageUrl: String?,
    isProfilePicLoading: Boolean,
    onProfilePicLoad: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onProfilePicClick: (() -> Unit?)? = null,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(100.dp)
            .clickable {
                if (onProfilePicClick != null) {
                    onProfilePicClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(
                    if (Build.VERSION.SDK_INT >= 28) {
                        ImageDecoderDecoder.Factory()
                    } else {
                        GifDecoder.Factory()
                    }
                )
                .data(
                    if (imageUrl.isNullOrEmpty()) {
                        R.drawable.profile_placeholder
                    } else imageUrl
                )
                .scale(Scale.FILL)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build(),
            onSuccess = {
                onProfilePicLoad(false)
            },
            placeholder = painterResource(R.drawable.profile_placeholder),
            contentScale = ContentScale.Crop,
            alpha = if (isProfilePicLoading) 0.5f else 1f,
            contentDescription = "Profile Picture"
        )
        if (isProfilePicLoading) {
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
        }
    }
}