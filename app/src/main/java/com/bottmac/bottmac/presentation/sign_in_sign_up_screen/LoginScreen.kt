package com.bottmac.bottmac.presentation.sign_in_sign_up_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bottmac.bottmac.R
import com.bottmac.bottmac.firebase_service.FireBaseService
import com.bottmac.bottmac.google_sign_in_service.SignedInState
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.BrowseAsGuest
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.InputType
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.SignInSignUpButton
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.TextInput
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun LoginScreen(
    state: SignedInState,
    onSignInClick: () -> Unit,
    navController: NavController,
    userType: (Int) -> Unit,
) {
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val firebase = FireBaseService()
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    ProvideWindowInsets {
        LazyColumn(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                Column(
                    modifier = Modifier
                        .navigationBarsWithImePadding()
                        .padding(start = 24.dp, end = 24.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                    )
                    TextInput(
                        inputType = InputType.Email,
                        keyboardActions = KeyboardActions(onNext = {
                            passwordFocusRequester.requestFocus()
                        }),
                        value = email,
                        onValueChange = { email = it },
                        hasError = false
                    )
                    TextInput(
                        inputType = InputType.Password,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = password,
                        pass = "L",
                        onValueChange = { password = it },
                        hasError = false
                    )
                    TextButton(onClick = {
                        if (isValidEmail(email)) {
                            firebase.sendPasswordResetLink(email)
                            println("Password Reset link has been sent to your email")
                        } else {
                            println("Enter a Valid Email Address")
                        }
                    }) {
                        Text(text = "Forgot Password?")
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .navigationBarsWithImePadding()
                        .padding(start = 24.dp, end = 24.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val isValidCredential = (isValidEmail(email) && isValidPassword(password))
                    SignInSignUpButton(
                        btnText = stringResource(R.string.sign_in),
                        name = "",
                        phoneNumber = "",
                        email = email,
                        password = password,
                        isValidCredential = isValidCredential,
                        onSignInClick = onSignInClick,
                        navController = navController,
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 28.dp,
                            end = 28.dp,
                            top = 36.dp,
                            bottom = 28.dp
                        ),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    BrowseAsGuest(
                        state = state,
                        onSignInClick = onSignInClick,
                        userType = { userType(0) }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don't have an account?")
                        TextButton(onClick = {
                            navController.navigate(NavigationRoutes.SignUp.route)
                            /*userType(2)*/
                        }) {
                            Text(text = "SIGN UP")
                        }
                    }
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._]+@[a-z]+\\.[a-z]{2,}"
    return Regex(emailPattern).matches(email)
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 8
            && password.isNotEmpty()
            && (password.any { it.isUpperCase() })
            && (password.any { it.isLowerCase() })
            && (password.any { !it.isLetterOrDigit() })
            && (password.any { it.isDigit() })
}