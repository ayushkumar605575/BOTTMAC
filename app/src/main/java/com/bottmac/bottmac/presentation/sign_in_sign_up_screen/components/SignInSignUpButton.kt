package com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bottmac.bottmac.email_sign_in_service.EmailSignInSignUpClient
import com.bottmac.bottmac.ui.theme.btnPrimary
import com.bottmac.bottmac.ui.theme.btnSecondary

@Composable
fun SignInSignUpButton(
    btnText: String,
    name: String,
    phoneNumber: String,
    email: String,
    password: String,
    isValidCredential: Boolean,
    userType: (Int) -> Unit
) {
    val emailSignInSignUpClient = EmailSignInSignUpClient()
    var waiting by rememberSaveable {
        mutableStateOf(false)
    }
    var isVerifiedUser by rememberSaveable {
        mutableStateOf(false to 0)
    }
    if (waiting || isVerifiedUser.first) {
        LaunchedEffect(key1 = Unit) {
            if (btnText == "SIGN IN") {
                isVerifiedUser = emailSignInSignUpClient.signInWithEmailAndPassword(email, password)
                waiting = false
                if (isVerifiedUser.first) {
                    userType(0)
                } else {
                    if (isVerifiedUser.second == 0) {
                        println("Incorrect UserName or Password")
                    }
                }
            } else {
                if (emailSignInSignUpClient.signUpWithEmailAndPassword(
                        name,
                        phoneNumber,
                        email,
                        password
                    )
                ) {
                    emailSignInSignUpClient.signOut()
                    userType(1)
                } else {
                    println("Email or Password in incorrect")
                }
            }
        }
    }


    Column {
        if (!isVerifiedUser.first && isVerifiedUser.second == 1) {
            println("Please verify the email") // TODO Check this line
            TextButton(onClick = {
                emailSignInSignUpClient.verifyEmail()
            }) {
                Text(text = "SEND EMAIL VERIFICATION LINK")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            enabled = !waiting && !isVerifiedUser.first,
            onClick = {
                if (!isValidCredential) {
                    println("Email or password is Empty")
                } else {
                    waiting = !waiting
                }
            },
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(48.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 0.dp,
                focusedElevation = 4.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                btnPrimary,
                                btnSecondary
                            )
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = btnText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (waiting || isVerifiedUser.first) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}
