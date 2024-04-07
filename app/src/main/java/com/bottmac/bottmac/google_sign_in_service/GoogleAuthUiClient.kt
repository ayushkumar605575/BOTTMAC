package com.bottmac.bottmac.google_sign_in_service

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.bottmac.bottmac.R
import com.bottmac.bottmac.userdata.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    val oldUser = db.document("users/${uid}").get().await().data
                    if (oldUser == null) {
                        db.document("users/${uid}").set(
                            hashMapOf(
                                "name" to displayName,
                                "phoneNumber" to if (phoneNumber == null) "" else phoneNumber,
                                "email" to email,
                                "profileImageUrl" to photoUrl.toString()
                            )
                        )
                        UserData(
                            userId = uid,
                            userName = displayName,
                            profilePicUrl = if (photoUrl == null) "" else photoUrl.toString(),
                            phoneNumber = phoneNumber,
                            email = email
                        )
                    } else {
                        UserData(
                            userId = uid,
                            userName = oldUser["name"].toString()
                                .ifEmpty { displayName },
                            profilePicUrl = oldUser["profileImageUrl"].toString()
                                .ifEmpty { "" },
                            phoneNumber = oldUser["phoneNumber"].toString()
                                .ifEmpty { phoneNumber },
                            email = oldUser["email"].toString()
                                .ifEmpty { email }
                        )
                    }
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getSignedInUser(): UserData {
        val user = auth.currentUser
        println("User $user")
        if (user != null) {
            if (!user.isEmailVerified) {
                auth.signOut()
            }
            val userData = try {
                db.document("users/${user.uid}").get().await().data
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
//            println("Signed In $userData")
//            if (user.displayName?.isNotBlank() == true) {
            return UserData(
                userId = user.uid,
                userName = if (userData != null) userData["name"].toString()
                    .ifEmpty { user.displayName } else user.displayName,
                profilePicUrl = if (userData != null) userData["profileImageUrl"].toString()
                    .ifEmpty { if (user.photoUrl == null) "" else user.photoUrl.toString() } else "",
                phoneNumber = if (userData != null) userData["phoneNumber"].toString()
                    .ifEmpty { user.phoneNumber } else user.phoneNumber,
                email = if (userData != null) userData["email"].toString()
                    .ifEmpty { user.email } else user.email
            )
//            } else {
//                if (userData != null) {
//                    return UserData(
//                        userId = auth.currentUser!!.uid,
//                        userName = userData["name"].toString(),
//                        profilePicUrl = userData["profileImageUrl"].toString(),
//                        phoneNumber = userData["phoneNumber"].toString(),
//                        email = userData["email"].toString()
//                    )
//                }
//            }
        }
        return UserData(null, null, null, null, null)
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}