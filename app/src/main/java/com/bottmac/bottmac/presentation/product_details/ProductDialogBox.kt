package com.bottmac.bottmac.presentation.product_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun ProductDialogBox(
    onDismiss: () -> Unit
) {
    val companyPhoneNumbers = listOf("+919718998222", "+919718228222", "+919818228222")
    var selectedPhoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            if (selectedPhoneNumber.isNotEmpty()){
                TextButton(
                    onClick = {
                        startActivity(
                            context,
                            Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$selectedPhoneNumber")
                            },
                            Bundle()
                        )
                    }
                ) {
                    Text("Call")
                }
            }
        },
        title = { Text(text = "Call Now") },
        text = {
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(companyPhoneNumbers) { phoneNumber ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                selectedPhoneNumber = phoneNumber
                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                        ) {
                            Text(
                                text = phoneNumber, fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (selectedPhoneNumber == phoneNumber){
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            HorizontalDivider()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PrevDialog() {
    ProductDialogBox {}
}