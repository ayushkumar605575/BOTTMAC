package com.bottmac.bottmac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.navigation.NavGraph
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint


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
//                    val viewModel = viewModel<SignInViewModel>()
//                    val state by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUiClient.getSignedInUser().userId != null) {
                            navController.navigate(NavigationRoutes.Home.route)
                        }
                    }

//                    val launcher =
//                        rememberLauncherForActivityResult(
//                            contract = ActivityResultContracts.StartIntentSenderForResult()
//                        ) { result ->
//                            if (result.resultCode == RESULT_OK) {
//                                lifecycleScope.launch {
//                                    val signInResult = googleAuthUiClient.signInWithIntent(
//                                        intent = result.data ?: return@launch
//                                    )
//                                    viewModel.onSignInResult(signInResult)
//                                }
//                            }
//                        }

                    NavGraph(navController = navController, googleAuthUiClient)
//                    when (userType) {
//                        1 -> {
//                            LoginScreen(
//                                state = state,
//                                onSignInClick = {
//                                    lifecycleScope.launch {
//                                        val signInIntentSender = googleAuthUiClient.signIn()
//                                        launcher.launch(
//                                            IntentSenderRequest.Builder(
//                                                signInIntentSender ?: return@launch
//                                            ).build()
//                                        )
//                                    }
//                                },
//                                userType = { userType = it },
//                            )
//                        }
//
//                        2 -> {
//                            SignUpScreen(
//                                state = state,
//                                onSignInClick = {
//                                    lifecycleScope.launch {
//                                        val signInIntentSender = googleAuthUiClient.signIn()
//                                        launcher.launch(
//                                            IntentSenderRequest.Builder(
//                                                signInIntentSender ?: return@launch
//                                            ).build()
//                                        )
//                                    }
//                                },
//                                userType = { userType = it }
//                            )
//                        }
//
//                        3 -> {
//                            Column(
//                                modifier = Modifier.fillMaxSize(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ){
//                                CircularProgressIndicator(Modifier.size(80.dp))
//                                Spacer(modifier = Modifier.height(20.dp))
//                                Text(
//                                    text = "Loading",
//                                    fontStyle = FontStyle.Italic,
//                                    fontSize = 20.sp
//                                )
//                            }
//                        }
//
//                        else -> {
//                            MainScreenAfterSignIn(
//                                navController = navController,
//                                userType = {
//                                    userType = it
//                                    navController.navigate(NavigationRoutes.Home.route)
//                                },
//                            )
//                        }
//                    }
                }
            }
        }
    }
}
