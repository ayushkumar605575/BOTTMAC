package com.bottmac.bottmac.presentation.main_screen.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.firebase_service.FireBaseService
import com.bottmac.bottmac.presentation.main_screen.displayToast
import com.bottmac.bottmac.userdata.UserData

@Composable
fun EditProfileScreen(
    modifier: Modifier,
    userData: UserData,
    onDetailsUpdate: () -> Unit
) {
    val fireStore = FireBaseService()
    val context = LocalContext.current
    var isImageUploading by rememberSaveable {
        mutableStateOf(true)
    }
    val firebaseServices = FireBaseService()
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                isImageUploading = true
                firebaseServices.uploadImage(uri) {
//                    isImageUploading = it
                    onDetailsUpdate()
                }
            }
        }
    var isUpdating by rememberSaveable {
        mutableStateOf(false)
    }
    var userName by rememberSaveable {
        mutableStateOf(userData.userName!!)
    }

    var userPhoneNumber by rememberSaveable {
        mutableStateOf(userData.phoneNumber.let { it ?: "" })
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ProfilePicComp(
            imageUrl = userData.profilePicUrl,
            isProfilePicLoading = isImageUploading,
            onProfilePicLoad = {
                isImageUploading = it
            },
            onProfilePicClick = {
                galleryLauncher.launch("image/*")
            }
        )

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text(text = "Name", maxLines = 1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraLarge,
            singleLine = true,
        )

        OutlinedTextField(
            value = userPhoneNumber,
            onValueChange = { userPhoneNumber = if (it.length >= 13) it.substring(0,13) else it },
            label = { Text(text = "Phone Number", maxLines = 1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraLarge,
            singleLine = true,
        )

    }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        ElevatedButton(onClick = {
            if (userName.isNotEmpty() && userPhoneNumber.isNotEmpty()) {
                isUpdating = true
                fireStore.updateProfileDetails(userName, userPhoneNumber) {
                    isUpdating = it
                    onDetailsUpdate()
                    displayToast(context, "Details Updated")
                }
            }
        }) {
            AnimatedContent(targetState = isUpdating, label = "") { updated ->
                if (updated) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeCap = StrokeCap.Round
                    )
                } else {
                    Text(text = "Apply Changes")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
