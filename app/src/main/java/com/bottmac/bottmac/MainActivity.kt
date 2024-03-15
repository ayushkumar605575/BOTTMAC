package com.bottmac.bottmac

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.google_sign_in.SignInResult
import com.bottmac.bottmac.presentation.google_sign_in.SignInViewModel
import com.bottmac.bottmac.presentation.google_sign_in.UserData
import com.bottmac.bottmac.screens.LoginPage
import com.bottmac.bottmac.screens.MainScreenAfterSignIn
import com.bottmac.bottmac.screens.NavigationRoutes
import com.bottmac.bottmac.screens.SignUpPage
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    var userType by rememberSaveable {       // 1 -> Sign In
                        // 2 -> Sign Up
                        // 0 -> Guest
                        mutableIntStateOf(1)
                    }
                    var isSignedIn by rememberSaveable {
                        mutableStateOf(false)
                    }
                    var signedInUser by rememberSaveable {
                        mutableStateOf<UserData?>(null)
                    }
                    LaunchedEffect(key1 = Unit) {
                        signedInUser = googleAuthUiClient.getSignedInUser()
                    }
                    val launcher =
                        rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult()
                        ) { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if (state.isSignInSuccessful) {
                        println("RUNNED")
                            Toast.makeText(
                                applicationContext,
                                "User Successfully Logged In",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.resetState()
                        }

                    }
                    when (userType) {
                        1 -> {
                            LoginPage(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                        signedInUser = googleAuthUiClient.getSignedInUser()
                                    }
                                },
                                navController = navController,
                                isGuest = { userType = it },
                                isSignedInUser = signedInUser != null,
                                signedInUserData = signedInUser
                            )
                        }

                        2 -> {
                            SignUpPage(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                        signedInUser = googleAuthUiClient.getSignedInUser()
                                    }
                                },
                                isGuest = { userType = it }
                            )
                        }
                        else -> {
                            MainScreenAfterSignIn(
                                navController = navController,
                                lifecycleScope = lifecycleScope,
                                googleAuthUiClient = googleAuthUiClient,
                                context = applicationContext,
                                isSigned = {
                                    isSignedIn = !isSignedIn
                                },
                                isGuest = {
                                    userType = it
                                    navController.navigate(NavigationRoutes.Home.route)

                                },
                                signedInUser = signedInUser
                                ,
                                state = state,
                                onSignInClick = {},
                            )
                        }
                    }
                }
            }
        }
    }
}
