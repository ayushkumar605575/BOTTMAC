package com.bottmac.bottmac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
//                    var isUserAlready by rememberSaveable {
//                        mutableStateOf(false)
//                    }
                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUiClient.getSignedInUser().userId != null) {
                            navController.navigate(NavigationRoutes.Home.route)
                        }
//                        isUserAlready = true
                    }
//                    if (isUserAlready) {
                        NavGraph(navController = navController, googleAuthUiClient)
//                    } else {
//                        CircularProgressIndicator()
//                    }
                }
            }
        }
    }
}
