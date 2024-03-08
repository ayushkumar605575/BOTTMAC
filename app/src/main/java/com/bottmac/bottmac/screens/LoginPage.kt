package com.bottmac.bottmac.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bottmac.bottmac.R
import com.bottmac.bottmac.ui.theme.btnPrimary
import com.bottmac.bottmac.ui.theme.btnSecondary
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun LoginPage() {
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    ProvideWindowInsets {
        LazyColumn(verticalArrangement = Arrangement.SpaceBetween) {
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
                        onValueChange = { email = it }
                    )
                    TextInput(
                        inputType = InputType.Password,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        value = password,
                        onValueChange = { password = it })
                    TextButton(onClick = { /*TODO*/ }) {
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
                    SignInSignUpButton(btnText = "SIGN IN")
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don't have an account?")
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "SIGN UP")
                        }
                    }
                }
            }
        }
    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    data object Email : InputType(
        label = "Email",
        icon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    data object Name : InputType(
        label = "Name",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    data object Password : InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )

    data object ConfirmPassword : InputType(
        label = "Confirm Password",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    value: String,
    pass: String? = null,
    onValueChange: (String) -> Unit
) {

    var passVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
        shape = MaterialTheme.shapes.extraLarge,
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = if (!passVisibility) inputType.visualTransformation else VisualTransformation.None,
        keyboardActions = keyboardActions,
        supportingText = {
            Column {
                if (inputType.label == "Password") {
                    if (value.length < 8) {
                        Text(text = "• Password length must be at least 8")
                    }
                    if (!value.any { it.isUpperCase() }) {
                        Text(text = "• Must contain Uppercase alphabets")
                    }
                    if (!value.any { it.isLowerCase() }) {
                        Text(text = "• Must contain Lowercase alphabets")
                    }
                    if (!value.any { !it.isLetterOrDigit() }) {
                        Text(text = "• Must contain a Special character")
                    }
                    if (!value.any { it.isDigit() }) {
                        Text(text = "• Must contain Numbers 1-9")
                    }
                } else if (inputType.label == "Confirm Password" && (value != pass || value.isEmpty())) {
                        Text(text = "• Password Doesn't Match")
                }
            }
        },
        trailingIcon = {
            if (inputType.label in setOf("Password", "Confirm Password")) {
                if (!passVisibility) {
                    Icon(
                        modifier = Modifier.clickable { passVisibility = !passVisibility },
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        modifier = Modifier.clickable { passVisibility = !passVisibility },
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            }
        }
    )
}


@Composable
fun SignInSignUpButton(btnText: String) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = { /*TODO*/ },
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
            Text(
                text = btnText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Login() {
    LoginPage()
}