package com.bottmac.bottmac.google_sign_in_service

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String?,
    val userName: String?,
    val profilePicUrl: String?,
    val phoneNumber: String?,
    val email: String?
)
