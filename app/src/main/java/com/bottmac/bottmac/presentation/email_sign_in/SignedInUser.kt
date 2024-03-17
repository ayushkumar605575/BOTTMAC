package com.bottmac.bottmac.presentation.email_sign_in

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.google_sign_in.UserData
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignedInUser @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _signedInUserData = MutableStateFlow(UserData(null, null, null, null, null))

    val signedInUserData: StateFlow<UserData>
        get() = _signedInUserData.asStateFlow()

    private val context = getApplication<Application>()
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(context.applicationContext)
        )
    }

    init {
        viewModelScope.launch {
            _signedInUserData.emit(googleAuthUiClient.getSignedInUser())
        }
    }

    fun signOutCurrentUser() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            Toast.makeText(
                context,
                "User Signed Out",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}