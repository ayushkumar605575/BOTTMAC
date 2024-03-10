package com.bottmac.bottmac.presentation.google_sign_in

data class SignedInState(
    val isSignInSuccessful: Boolean = false,
    val signError: String? = null
)
