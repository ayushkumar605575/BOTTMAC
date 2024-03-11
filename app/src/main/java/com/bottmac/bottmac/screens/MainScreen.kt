package com.bottmac.bottmac.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bottmac.bottmac.R
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.google_sign_in.SignInViewModel
import com.bottmac.bottmac.presentation.google_sign_in.SignedInState
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context
) {
//    var isSignedIn by rememberSaveable {
//        mutableStateOf(false)
//    }
//    var isGuest by rememberSaveable {
//        mutableStateOf(false)
//    }
//    val viewModel = viewModel<SignInViewModel>()
//    val state by viewModel.state.collectAsStateWithLifecycle()
//
//    LaunchedEffect(key1 = Unit) {
//        if (googleAuthUiClient.getSignedInUser() != null) {
//            isSignedIn = !isSignedIn
////            navController.navigate(NavigationRoutes.Home.route)
//        }
//    }
//
//    if (!isSignedIn && !isGuest) {
//        val launcher =
//            rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.StartIntentSenderForResult()
//            ) { result ->
//                if (result.resultCode == ComponentActivity.RESULT_OK) {
//                    lifecycleScope.launch {
//                        val signInResult = googleAuthUiClient.signInWithIntent(
//                            intent = result.data ?: return@launch
//                        )
//                        viewModel.onSignInResult(signInResult)
//                    }
//                }
//            }
//        LaunchedEffect(key1 = state.isSignInSuccessful) {
//            if (state.isSignInSuccessful) {
//                Toast.makeText(
//                    context,
//                    "User Successfully Logged In",
//                    Toast.LENGTH_LONG
//                ).show()
//                isSignedIn = !isSignedIn
////            navController.navigate(NavigationRoutes.Home.route)
//                viewModel.resetState()
//            }
//
//        }
//        NavGraph(
//            state = state,
//            onSignInClick = {
//                lifecycleScope.launch {
//                val signInIntentSender = googleAuthUiClient.signIn()
//                launcher.launch(
//                    IntentSenderRequest.Builder(
//                        signInIntentSender ?: return@launch
//                    ).build()
//                )
//            } },
//            navController = navController,
//            paddingValues = PaddingValues(24.dp),
//            lifecycleScope = lifecycleScope,
//            googleAuthUiClient = googleAuthUiClient,
//            context = context,
//            isGuest = {
//                isGuest = !isGuest
//                navController.navigate(NavigationRoutes.Home.route)
//                      },
//            isSigned = { isSignedIn = !isSignedIn }
//        )
//        LoginPage(
//            state = state,
//            onSignInClick = {
//
//            },
//            navController = navController
//        )
//    } else {

    }


//    val navController = rememberNavController()


//                    LoginPage()
//                    println(isComp.isCompleted)
//                    if (isComp.isCompleted) {
//                        LazyColumn {
//                            items(res) { productItem ->
//                                ProductCard(
//                                    productName = productItem.productName,
//                                    productsFeatures = productItem.productFeatures.split("\\n"),
//                                    productsImages = productItem.productImage
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                            }
//                        }
//                    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAfterSignIn(
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    isSigned: () -> Unit,
    isGuest: () -> Unit,
    state: SignedInState,
    onSignInClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
//                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    )
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                            )
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 32.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        MaterialTheme.colorScheme.secondaryContainer
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(NavigationRoutes.Search.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                            )
                        }
                    }
                )
            },
            bottomBar = { BottomBar(navController = navController) }
        ) { paddingValues ->
            NavGraph(
                state = state,
                onSignInClick = onSignInClick,
                navController = navController,
                paddingValues = paddingValues,
                lifecycleScope = lifecycleScope,
                googleAuthUiClient = googleAuthUiClient,
                context = context,
                isSigned = isSigned,
                isGuest = isGuest
            )
//                            LoginPage(paddingValues)
//                        SignUpPage(paddingValues)
        }
    }
}