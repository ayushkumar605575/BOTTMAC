package com.bottmac.bottmac.presentation.sign_in_sign_up_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    value: String,
    pass: String? = null,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false
) {

    var passVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        isError = hasError,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label, maxLines = 1) },
        shape = MaterialTheme.shapes.extraLarge,
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = if (!passVisibility) inputType.visualTransformation else VisualTransformation.None,
        keyboardActions = keyboardActions,
        supportingText = {
            Column {
                if (inputType.label == "Name" && hasError) {
                    Text(text = "• Cannot be empty.")
                }

                if(inputType.label == "Phone Number"  && hasError) {
                    Text(text = "• Cannot be empty.")
                }

                if (inputType.label == "Password" && pass != "L") {
                    if (value.length < 8) {
                        Text(text = "• Password length must be at least 8")
                    }
                    if (!value.any { it.isUpperCase() }) {
                        Text(text = "• Must contain Uppercase alphabets")
                    }
                    if (!value.any { it.isLowerCase() }) {
                        Text(text = "• Must contain Lowercase alphabets")
                    }
                    if (!value.any { !it.isLetterOrDigit() }) {
                        Text(text = "• Must contain a Special character")
                    }
                    if (!value.any { it.isDigit() }) {
                        Text(text = "• Must contain Numbers 1-9")
                    }
                } else if (hasError && inputType.label == "Confirm Password" && (value != pass || value.isEmpty())) {
                    Text(text = "• Password Doesn't Match")
                } else if (hasError && inputType.label == "Email") {
                    Text(text = "• Invalid Email")
                }
            }
        },
        trailingIcon = {
            if (inputType.label in setOf("Password", "Confirm Password")) {
                if (!passVisibility) {
                    Icon(
                        modifier = Modifier.clickable { passVisibility = !passVisibility },
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        modifier = Modifier.clickable { passVisibility = !passVisibility },
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
