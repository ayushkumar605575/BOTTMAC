package com.bottmac.bottmac.firebase_service

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