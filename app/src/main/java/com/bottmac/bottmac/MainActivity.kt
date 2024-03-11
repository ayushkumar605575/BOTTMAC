package com.bottmac.bottmac

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.presentation.email_sign_in.EmailSignInSignUpClient
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.google_sign_in.SignInViewModel
import com.bottmac.bottmac.screens.LoginPage
import com.bottmac.bottmac.screens.MainScreen
import com.bottmac.bottmac.screens.MainScreenAfterSignIn
import com.bottmac.bottmac.screens.NavGraph
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

//    private val auth = FirebaseAuth.getInstance()

    @Inject
    lateinit var api: ProductsApi
//    private lateinit var res: List<ProductItem>

    //    @OptIn(DelicateCoroutinesApi::class)
//    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val isComp = GlobalScope.launch {
//            val response = api.getProducts()
//            if (response.isSuccessful) {
//                res = response.body()!!
//            }
//        val signUp = EmailSignInSignUpClient(applicationContext)
//        signUp.signInWithEmailAndPassword("ayushkumar605575@gmail.com","Ayush!@#123")
//        }
        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                    NavGraph(navController = navController, paddingValues = null, lifecycleScope = lifecycleScope, googleAuthUiClient)
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
                    LaunchedEffect(key1 = Unit) {
                        println(googleAuthUiClient.getSignedInUser())
                        if (googleAuthUiClient.getSignedInUser() != null) {
                            isSignedIn = !isSignedIn
//                            navController.navigate(NavigationRoutes.Home.route)
                        }
                    }

//                    if (!isSignedIn && !isGuest) {
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
                            Toast.makeText(
                                applicationContext,
                                "User Successfully Logged In",
                                Toast.LENGTH_LONG
                            ).show()
//                            isSignedIn = !isSignedIn
//            navController.navigate(NavigationRoutes.Home.route)
                            viewModel.resetState()
                        }

                    }
//                    NavGraph(
//                        state = state,
//                        onSignInClick = {
//                            lifecycleScope.launch {
//                                val signInIntentSender = googleAuthUiClient.signIn()
//                                launcher.launch(
//                                    IntentSenderRequest.Builder(
//                                        signInIntentSender ?: return@launch
//                                    ).build()
//                                )
//                            }
//                        },
//                        navController = navController,
//                        paddingValues = PaddingValues(24.dp),
//                        lifecycleScope = lifecycleScope,
//                        googleAuthUiClient = googleAuthUiClient,
//                        context = applicationContext,
//                        isGuest = {
////                            isGuest = !isGuest
//                            navController.navigate(NavigationRoutes.Home.route)
//                        },
//                        isSigned = { isSignedIn = !isSignedIn }
//                    )
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
                                    }
                                },
                                navController = navController,
                                isGuest = { userType = it }
                            )
                        }
                        2 -> {
                            SignUpPage(
                                state = state,
                                onSignInClick = { /*TODO*/ },
                                isGuest = { userType = it }
                            )
                        }
                        else -> {
//                        MainScreen(
//                            lifecycleScope = lifecycleScope,
//                            navController = navController,
//                            googleAuthUiClient = googleAuthUiClient,
//                            context = applicationContext
//                        )
                            println("DSJHKGIYUHILUYGSHDLISdgsildghISUDGHIDGoi")
                            MainScreenAfterSignIn(
                                navController = navController,
                                lifecycleScope = lifecycleScope,
                                googleAuthUiClient = googleAuthUiClient,
                                context = applicationContext,
                                isSigned = {
                                    isSignedIn = !isSignedIn
                                },
                                isGuest = {
//                                    isGuest = !isGuest
                                    navController.navigate(NavigationRoutes.Home.route)

                                },
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
