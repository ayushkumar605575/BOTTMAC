package com.bottmac.bottmac.presentation.email_sign_in

//import com.google.firebase.BuildConfig
//import com.google.firebase.appcheck.interop.BuildConfig
//import androidx.media3.ui.BuildConfig
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

//import com.google.firebase.components.BuildConfig

//import com.google.firebase.ktx.BuildConfig

class EmailSignInSignUpClient(
    val context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    fun signUpWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            println("Account Created")
            println(result.credential)
            println(result.user?.isEmailVerified)
            result.user?.sendEmailVerification()

        }
            .addOnFailureListener { e ->
                println(e.message)

            }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean = try {
        auth.signInWithEmailAndPassword(email, password).await().user?.isEmailVerified ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
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