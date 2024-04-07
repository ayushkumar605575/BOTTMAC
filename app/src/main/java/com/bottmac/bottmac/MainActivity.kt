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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.navigation.NavGraph
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private var _isLoading = MutableStateFlow(true)
    private var _isUser = MutableStateFlow(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {

            setKeepOnScreenCondition { // Splash Screen Shows Up until the condition is true
                lifecycleScope.launch {
                    _isUser.emit(googleAuthUiClient.getSignedInUser().userId != null)
                    _isLoading.emit(false)
                }
                _isLoading.value
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
                    val isUser by _isUser.collectAsState()
                    val isLoading by _isLoading.collectAsState()
                    val navController = rememberNavController()
                    if (isUser && !isLoading) {
                        println("User Loaded")
                        navController.navigate("mainScreen") {
                            popUpTo(NavigationRoutes.SignIn.route) {
                                inclusive = true
                            }
                        }
                    }
                    NavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient
                    )
                }
            }
        }
    }
}
