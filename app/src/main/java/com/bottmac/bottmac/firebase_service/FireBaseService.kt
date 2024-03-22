package com.bottmac.bottmac.firebase_service

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FireBaseService {
    private val auth = FirebaseAuth.getInstance()

    fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
    }
    suspend fun getTokenId(): String {
        println(auth.currentUser?.getIdToken(true)?.await()?.token?.length ?: "")
        return auth.currentUser?.getIdToken(true)?.await()?.token ?: ""
    }

    fun sendPasswordResetLink(email: String) {
        auth.sendPasswordResetEmail(email)
    }

}