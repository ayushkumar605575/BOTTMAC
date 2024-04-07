package com.bottmac.bottmac

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.navigation.NavGraph
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        installSplashScreen().apply {
            setKeepOnScreenCondition { // Splash Screen Shows Up until the condition is true
                var isLoading = true
                lifecycleScope.launch {
                    googleAuthUiClient.getSignedInUser()
//                    googleAuthUiClient.getSignedInUser().userId != null) {
                    isLoading = false
//                }
                }
                isLoading
            }
            setOnExitAnimationListener { splashScreens ->
                val zoomX = ObjectAnimator.ofFloat(
                    splashScreens.iconView,
                    View.SCALE_X,
                    0.6f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { splashScreens.remove() }
                val zoomY = ObjectAnimator.ofFloat(
                    splashScreens.iconView,
                    View.SCALE_Y,
                    0.6f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { splashScreens.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                    LaunchedEffect(key1 = Unit) {
//                        if (googleAuthUiClient.getSignedInUser().userId != null) {
//                            navController.navigate("mainScreen") {
//                                popUpTo("startUp") {
//                                    inclusive = true
//                                }
//                            }
//                        } else {
//                            navController.navigate(NavigationRoutes.SignIn.route) {
//                                popUpTo("startUp") {
//                                    inclusive = false
//                                }
//                            }
//                        }
//                    }
                    NavGraph(navController = navController, googleAuthUiClient)
                }
            }
        }
    }
}
