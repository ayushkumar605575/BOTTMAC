package com.bottmac.bottmac.google_sign_in_service

data class SignedInState(
    val isSignInSuccessful: Boolean = false,
    val signError: String? = null
)
