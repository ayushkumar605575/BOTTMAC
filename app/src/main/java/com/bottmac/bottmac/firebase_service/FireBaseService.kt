package com.bottmac.bottmac.firebase_service

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FireBaseService {
    private val auth = FirebaseAuth.getInstance()
    private val storageRef = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()

    //    fun get st
    fun uploadImage(
        profilePic: Uri,
        isUploading: () -> Unit
    ) {
        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            storageRef.getReference(uid).putFile(profilePic)
                .addOnSuccessListener { profileUploadTask ->
                    db.document("users/${uid}").get().addOnSuccessListener { snapshot ->
                        profileUploadTask.storage.downloadUrl.addOnSuccessListener { downloadLink ->
                            snapshot.reference.update(
                                mapOf<String, String>("profileImageUrl" to downloadLink.toString())
                            )
                            isUploading()
                        }
                    }
                }
        } else {
            isUploading()
        }
    }

//    fun sendEmailVerification() {
//        auth.currentUser?.sendEmailVerification()
//    }

    suspend fun getTokenId(): String {
        println(auth.currentUser?.getIdToken(true)?.await()?.token?.length ?: "")
        return auth.currentUser?.getIdToken(true)?.await()?.token ?: "BottMac@Guest?User"
    }

    fun sendPasswordResetLink(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    fun updateProfileDetails(
        userName: String,
        userPhoneNumber: String,
        isUpdating: (Boolean) -> Unit
    ) {
        val user = auth.currentUser
        if (user != null) {
            db.document("users/${user.uid}").get().addOnSuccessListener { snapshot ->
                snapshot.reference.update(
                    mapOf<String, String>("name" to userName, "phoneNumber" to userPhoneNumber)
                )
                isUpdating(false)
            }
        } else {
            println("EEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRR")
        }
    }
}