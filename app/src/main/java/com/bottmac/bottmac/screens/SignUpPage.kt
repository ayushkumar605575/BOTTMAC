package com.bottmac.bottmac.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.R
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun SignUpPage() {
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
    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }
    ProvideWindowInsets {
        LazyColumn(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                )
            }
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
                TextInput(
                    inputType = InputType.Email,
                    keyboardActions = KeyboardActions(onNext = {
//                        focusManager.clearFocus()
                        passwordFocusRequester.requestFocus()
                    }),
                    focusRequester = passwordFocusRequester,
                    value = email,
                    onValueChange = { email = it }
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
                    onValueChange = { password = it }
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
                    onValueChange = { confirmPassword = it }
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { SignInSignUpButton(btnText = "SIGN UP") }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = 48.dp,
                        end = 48.dp,
                        top = 48.dp,
                        bottom = 28.dp
                    ),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Gray),
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.g),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have account?")
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "SIGN IN")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun SignUpPrev() {
    SignUpPage()
}