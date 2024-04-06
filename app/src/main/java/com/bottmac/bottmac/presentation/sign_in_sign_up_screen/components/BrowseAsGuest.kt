package com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bottmac.bottmac.R
import com.bottmac.bottmac.google_sign_in_service.SignedInState
import com.bottmac.bottmac.presentation.main_screen.displayToast

@Composable
fun BrowseAsGuest(
    state: SignedInState,
    onSignInClick: () -> Unit,
    navController: NavController
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signError) {
        state.signError?.let { error ->
            displayToast(context, error)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = onSignInClick,
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

        TextButton(
            onClick = {
                navController.navigate("mainScreen") {
                    popUpTo("startUp") {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )

        ) {
            Text(
                text = "Browse as Guest",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = null)
        }
    }
}
