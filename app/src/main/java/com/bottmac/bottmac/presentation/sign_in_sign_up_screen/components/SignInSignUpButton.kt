package com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bottmac.bottmac.email_sign_in_service.EmailSignInSignUpClient
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.presentation.main_screen.displayToast

@Composable
fun SignInSignUpButton(
    btnText: String,
    name: String,
    phoneNumber: String,
    email: String,
    password: String,
    isValidCredential: Boolean,
    hasConsent: Boolean? = null,
    navController: NavController,
    onSignInClick: () -> Unit,
    onInvalidSignIn: () ->Unit,
) {
    val context = LocalContext.current
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
                println(isVerifiedUser)
                waiting = false
                if (isVerifiedUser.first) {
                    onSignInClick()
                    navController.navigate("mainScreen") {
                        popUpTo("startUp") {
                            inclusive = true
                        }
                    }
                } else {
                    if (isVerifiedUser.second == 0) {
                        displayToast(context = context, msg = "Incorrect UserName or Password")
                    } else {
                        onInvalidSignIn()
                    }
                }
            } else {
                if (name.isEmpty() || phoneNumber.isEmpty()) {
                    waiting = false
                    return@LaunchedEffect
                } else if (emailSignInSignUpClient.signUpWithEmailAndPassword(
                        name,
                        phoneNumber,
                        email,
                        password
                    )
                ) {
                    displayToast(
                        context = context,
                        msg = "Verification link has been sent to your mail."
                    )
                    emailSignInSignUpClient.signOut()
                    onSignInClick()
                    navController.navigate(NavigationRoutes.SignIn.route) {
                        popUpTo("main") {
                            inclusive = true
                        }
                    }
                } else {
                    displayToast(context = context, msg = "Email or Password in incorrect")
                }
            }
        }
    }


    Column {
        if (!isVerifiedUser.first && isVerifiedUser.second == 1) {
            displayToast(context = context, msg = "Please verify the email") // TODO Check this line
            TextButton(
                modifier = Modifier.padding(start = 20.dp),
                onClick = {
                    emailSignInSignUpClient.verifyEmail()
                    emailSignInSignUpClient.signOut()
                }) {
                Text(text = "SEND EMAIL VERIFICATION LINK")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(
            modifier = Modifier
                .heightIn(48.dp),
            enabled = !waiting && !isVerifiedUser.first,
            onClick = {
                if (btnText != "SIGN IN"){
                    if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        displayToast(context = context, msg = "Complete the Details")
                    }
                    else if (hasConsent != null && !hasConsent) {
                        displayToast(context = context, msg = "Please agree the consent!")
                    }
                }
                if (!isValidCredential) {
                    displayToast(context = context, msg = "Incorrect Email or Password")
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
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
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
                    AnimatedContent(
                        targetState = waiting || isVerifiedUser.first,
                        label = ""
                    ) { isSigning ->
                        if (isSigning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                text = btnText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                        }
                    }
                }
            }
        }
    }
}
