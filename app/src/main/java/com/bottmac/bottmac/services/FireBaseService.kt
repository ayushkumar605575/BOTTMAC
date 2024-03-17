package com.bottmac.bottmac.services

import com.google.firebase.auth.FirebaseAuth

class FireBaseService {
    private val auth = FirebaseAuth.getInstance()

    fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
    }

    fun sendPasswordResetLink(email: String) {
        auth.sendPasswordResetEmail(email)
    }

}