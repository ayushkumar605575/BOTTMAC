package com.bottmac.bottmac.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ShippingAddressScreenStructure(
//    navController: NavHostController
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.logo),
//                            contentDescription = null,
//                            modifier = Modifier.size(40.dp),
//                        )
//                        Text(
//                            text = ProfileOptions.ShippingAddress.title,
//                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                            fontSize = 28.sp,
//                            fontFamily = FontFamily.SansSerif,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    MaterialTheme.colorScheme.secondaryContainer
//                ),
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = null
//                        )
//                    }
//                },
//                actions = {
//
//                }
//            )
//        }
//    ) { paddingValues ->
//        println(navController.currentDestination?.route)
//        ShippingAddressScreen(modifier = Modifier.padding(paddingValues))
//    }
//}

@Composable
fun ShippingAddressScreen(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}
