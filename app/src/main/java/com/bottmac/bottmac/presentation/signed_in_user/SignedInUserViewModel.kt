package com.bottmac.bottmac.presentation.signed_in_user

import androidx.lifecycle.ViewModel
import com.bottmac.bottmac.presentation.google_sign_in.SignedInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignedInUserViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignedInState())
    val state = _state.asStateFlow()
}