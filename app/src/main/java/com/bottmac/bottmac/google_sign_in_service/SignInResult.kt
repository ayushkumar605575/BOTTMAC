package com.bottmac.bottmac.google_sign_in_service

import com.bottmac.bottmac.userdata.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

