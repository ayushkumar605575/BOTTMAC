package com.bottmac.bottmac.email_sign_in_service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EmailSignInSignUpClient {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
//    private var storageRef = FirebaseStorage.getInstance()

    fun verifyEmail() {
        if (auth.currentUser != null) {
            auth.currentUser!!.sendEmailVerification()
        }
    }

    suspend fun signUpWithEmailAndPassword(
        name: String,
        phoneNumber: String,
        email: String,
        password: String
    ): Boolean = try {
        val newUser = auth.createUserWithEmailAndPassword(email, password).await()
        newUser.user!!.sendEmailVerification()
        db.document("users/${newUser.user!!.uid}").set(
            hashMapOf(
                "name" to name,
                "phoneNumber" to phoneNumber,
                "email" to email,
                "profileImageUrl" to ""
            )
        )
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

//    suspend fun getSignedInUser() {
//        if (auth.currentUser != null) {
//            println(auth.currentUser!!.uid)
//            val userData = db.document("users/${auth.currentUser!!.uid}").get().await().data
//            if (userData != null) {
//                SignInResult(
//                    data = UserData(
//                        userId = auth.currentUser!!.uid,
//                        userName = userData["name"].toString(),
//                        profilePicUrl = userData["profileImageUrl"].toString(),
//                        phoneNumber = userData["phoneNumber"].toString(),
//                        email = userData["email"].toString()
//                    ),
//                    errorMessage = null
//                )
//            } else {
//                SignInResult(
//                    data = null,
//                    errorMessage = null
//                )
//            }
//        }
//    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Pair<Boolean, Int> =
        try {
            auth.signInWithEmailAndPassword(email, password)
                .await().user!!.isEmailVerified to 1 //?: false

        } catch (e: Exception) {
            false to 0
        }

    fun signOut() {
        auth.signOut()
    }
}