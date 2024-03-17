package com.bottmac.bottmac.presentation.google_sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.bottmac.bottmac.R
import com.bottmac.bottmac.presentation.email_sign_in.EmailSignInSignUpClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
    private var storageRef = FirebaseStorage.getInstance()

//    private val emailSignInSignUpClient = EmailSignInSignUpClient(context)

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
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePicUrl = photoUrl?.toString(),
                        phoneNumber = phoneNumber,
                        email = email
                    )
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
        if (auth.currentUser != null) {
            val user = auth.currentUser!!
            if (user.displayName!!.isNotBlank()) {
                return UserData(
                    userId = auth.currentUser!!.uid,
                    userName = user.displayName,
                    profilePicUrl = user.photoUrl.toString(),
                    phoneNumber = user.phoneNumber,
                    email = user.email
                )
            } else {
                val userData = try {
                    db.document("users/${auth.currentUser!!.uid}").get().await().data
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                if (userData != null) {
                    return UserData(
                        userId = auth.currentUser!!.uid,
                        userName = userData["name"].toString(),
                        profilePicUrl = userData["profileImageUrl"].toString(),
                        phoneNumber = userData["phoneNumber"].toString(),
                        email = userData["email"].toString()
                    )
                }
            }
        }
        return UserData(null,null,null,null,null)
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