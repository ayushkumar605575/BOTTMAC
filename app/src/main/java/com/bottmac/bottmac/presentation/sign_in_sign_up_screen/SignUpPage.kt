package com.bottmac.bottmac.presentation.sign_in_sign_up_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.R
import com.bottmac.bottmac.google_sign_in_service.SignedInState
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.BrowseAsGuest
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.InputType
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.SignInSignUpButton
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components.TextInput
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun SignUpScreen(
    state: SignedInState,
    onSignInClick: () -> Unit,
    userType: (Int) -> Unit
) {
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumberWithCountryCode by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }
    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
            )
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TextInput(
                        inputType = InputType.Name,
                        keyboardActions = KeyboardActions(onNext = {
//                        focusManager.clearFocus()
                            passwordFocusRequester.requestFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = userName,
                        onValueChange = { userName = it }
                    )
                }
                item {
                    // TODO Add Phone Number With Country Code
                    TextInput(
                        inputType = InputType.PhoneNumber,
                        keyboardActions = KeyboardActions(onNext = {
//                        focusManager.clearFocus()
                            passwordFocusRequester.requestFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = phoneNumberWithCountryCode,
                        onValueChange = { phoneNumberWithCountryCode = it }
                    )
                }
                item {
                    TextInput(
                        inputType = InputType.Email,
                        keyboardActions = KeyboardActions(onNext = {
//                        focusManager.clearFocus()
                            passwordFocusRequester.requestFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = email,
                        onValueChange = { email = it },
                        hasError = !isValidEmail(email)
                    )
                }
                item {
                    TextInput(
                        inputType = InputType.Password,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = password,
                        onValueChange = { password = it },
                        hasError = !isValidPassword(password)
                    )
                }
                item {
                    TextInput(
                        inputType = InputType.ConfirmPassword,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = confirmPassword,
                        pass = password,
                        onValueChange = { confirmPassword = it },
                        hasError = !isValidPassword(confirmPassword) || (password != confirmPassword)
                    )
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item {
                    val isValidCredential = (isValidEmail(email) && isValidPassword(password))
                    SignInSignUpButton(
                        btnText = "SIGN UP",
                        name = userName,
                        phoneNumber = phoneNumberWithCountryCode,
                        email = email,
                        password = password,
                        isValidCredential = isValidCredential,
                        userType = userType
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 48.dp,
                            end = 48.dp,
                            top = 28.dp,
                            bottom = 28.dp
                        ),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                item {
                    BrowseAsGuest(
                        state = state,
                        onSignInClick = onSignInClick,
                        userType = { userType(0) }
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Already have account?")
                        TextButton(onClick = { userType(1) }) {
                            Text(text = "SIGN IN")
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}