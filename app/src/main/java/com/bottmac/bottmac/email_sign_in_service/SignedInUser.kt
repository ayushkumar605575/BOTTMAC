package com.bottmac.bottmac.email_sign_in_service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.main_screen.displayToast
import com.bottmac.bottmac.userdata.UserData
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignedInUser @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _signedInUserData = MutableStateFlow(UserData(null, "", "", null, null))

    val signedInUserData: StateFlow<UserData>
        get() = _signedInUserData.asStateFlow()

    private val context = getApplication<Application>()
    private val googleAuthUiClient: GoogleAuthUiClient =
        GoogleAuthUiClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(context.applicationContext)
        )

    fun getUserUpdatedData() {
        viewModelScope.launch {
            _signedInUserData.emit(googleAuthUiClient.getSignedInUser())
        }
    }

    init {
        getUserUpdatedData()
    }

    fun signOutCurrentUser() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            displayToast(context, "User Signed Out")
        }
    }


}