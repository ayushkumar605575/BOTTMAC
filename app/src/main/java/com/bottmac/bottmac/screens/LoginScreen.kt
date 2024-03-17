package com.bottmac.bottmac.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bottmac.bottmac.R
import com.bottmac.bottmac.presentation.email_sign_in.EmailSignInSignUpClient
import com.bottmac.bottmac.presentation.google_sign_in.SignedInState
import com.bottmac.bottmac.services.FireBaseService
import com.bottmac.bottmac.ui.theme.btnPrimary
import com.bottmac.bottmac.ui.theme.btnSecondary
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun LoginScreen(
    state: SignedInState,
    onSignInClick: () -> Unit,
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
                        userType = userType
//                        navController = navController
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
                    GoogleOrGuest(
                        state = state,
                        onSignInClick = onSignInClick,
                        userType = { userType(0) }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don't have an account?")
                        TextButton(onClick = { userType(2) }) {
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

    data object PhoneNumber : InputType(
        label = "Phone Number",
        icon = Icons.Default.Phone,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
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
    onValueChange: (String) -> Unit,
    hasError: Boolean = false
) {

    var passVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        isError = hasError,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label, maxLines = 1) },
//        ime,
        shape = MaterialTheme.shapes.extraLarge,
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = if (!passVisibility) inputType.visualTransformation else VisualTransformation.None,
        keyboardActions = keyboardActions,
        supportingText = {
            Column {
                if (inputType.label == "Password" && pass != "L") {
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
                } else if (hasError && inputType.label == "Confirm Password" && (value != pass || value.isEmpty())) {
                    Text(text = "• Password Doesn't Match")
                } else if (hasError && inputType.label == "Email") {
                    Text(text = "• Invalid Email")
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
fun SignInSignUpButton(
    btnText: String,
    name: String,
    phoneNumber: String,
    email: String,
    password: String,
    isValidCredential: Boolean,
    userType: (Int) -> Unit
//    navController: NavHostController
) {
    val emailSignInSignUpClient = EmailSignInSignUpClient(LocalContext.current)
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
//        navController.navigate(NavigationRoutes.Home.route)
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
                    userType(0)
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
                emailSignInSignUpClient.verifyEmail(email)
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
//           TODO       FirebaseAuth.getInstance().signInWithEmailAndPassword()

                if (!isValidCredential) {
                    println("Email or password is Empty")
                } else {
                    waiting = !waiting
                }
//            if (
//                isValidCredential
//                && emailSignInSignUpClient.signInWithEmailAndPassword(email, password)
//                ) {
//            } else {
//                println("Email not Verified.")
//            }
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

//@Preview(showBackground = true)
//@Composable
//private fun Login() {
//    LoginPage()
//}