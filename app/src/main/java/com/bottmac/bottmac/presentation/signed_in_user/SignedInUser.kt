package com.bottmac.bottmac.presentation.signed_in_user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.presentation.google_sign_in.UserData
import okhttp3.internal.wait

@Composable
fun SignedInUserScreen(signedInUser: UserData) {

}

@Preview
@Composable
private fun SignedInUserPrev() {
    SignedInUserScreen(
        signedInUser = UserData(
            "",
            "Ayush Kumar",
            "",
            "+914837392923",
            "ayushkumar605575@gmail.com"
        )
    )
}