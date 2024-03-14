package com.bottmac.bottmac.presentation.email_sign_in

//import com.google.firebase.BuildConfig
//import com.google.firebase.appcheck.interop.BuildConfig
//import androidx.media3.ui.BuildConfig
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.bottmac.bottmac.presentation.google_sign_in.SignInResult
import com.bottmac.bottmac.presentation.google_sign_in.UserData
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

//import com.google.firebase.components.BuildConfig

//import com.google.firebase.ktx.BuildConfig

class EmailSignInSignUpClient(
    val context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var storageRef = FirebaseStorage.getInstance()

    fun verifyEmail(email: String) {
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

    suspend fun getSignedInUser() {
        if (auth.currentUser != null) {
            println(auth.currentUser!!.uid)
            val userData = db.document("users/${auth.currentUser!!.uid}").get().await().data
            if (userData != null) {
                SignInResult(
                    data = UserData(
                        userId = auth.currentUser!!.uid,
                        userName = userData["name"].toString(),
                        profilePicUrl = userData["profileImageUrl"].toString(),
                        phoneNumber = userData["phoneNumber"].toString(),
                        email = userData["email"].toString()
                    ),
                    errorMessage = null
                )
            } else {
                SignInResult(
                    data = null,
                    errorMessage = null
                )
            }
        }

//        db.collection(auth.currentUser!!.uid).get().addOnSuccessListener { doc ->
//            println(doc.)
//        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Pair<Boolean, Int> =
        try {
            auth.signInWithEmailAndPassword(email, password)
                .await().user!!.isEmailVerified to 1 //?: false

        } catch (e: Exception) {
            false to 0
        }
//        var isVerifiedUser = false
//.addOnSuccessListener { result ->
//            isVerifiedUser = result.user?.isEmailVerified ?: false
//        }
//            .addOnFailureListener { e ->
//                println(e.message)
//            }
//        return isVerifiedUser
//    }
}