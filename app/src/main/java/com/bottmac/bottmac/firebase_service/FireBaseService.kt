package com.bottmac.bottmac.firebase_service

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FireBaseService {
    private val auth = FirebaseAuth.getInstance()
    private val storageRef = FirebaseStorage.getInstance()// .getReference("${auth}.jpg")
    private val db = FirebaseFirestore.getInstance()

    //    fun get st
    fun uploadImage(
        profilePic: Uri,
        isUploading: (Boolean) -> Unit
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
                            isUploading(false)
                        }
                    }
                }
//            auth.currentUser!!.uid
        } else {
            isUploading(false)
        }
    }

    fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
    }

    suspend fun getTokenId(): String {
        println(auth.currentUser?.getIdToken(true)?.await()?.token?.length ?: "")
        return auth.currentUser?.getIdToken(true)?.await()?.token ?: "BottMac@Guest?User"
    }

    fun sendPasswordResetLink(email: String) {
        auth.sendPasswordResetEmail(email)
    }

}