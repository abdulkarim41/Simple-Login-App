package com.abdulkarim.simpleloginapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(R.drawable.ic_login_logo),
            contentDescription = "",
            modifier = Modifier.height(150.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LoginFrom(viewModel)

    }

}

@Composable
fun LoginFrom(viewModel: LoginViewModel){

    PhoneNumberTextField(
        value = viewModel.phoneNumberText,
        isPhoneNumberError = viewModel.isPhoneNumberError,
        phoneNumberErrorText = viewModel.phoneNumberErrorText,
        onValueChanged = {viewModel.onPhoneNumberTextChanged(it)}
    )

    Spacer(modifier = Modifier.height(16.dp))

    PasswordTextField(
        value = viewModel.passwordText,
        isPasswordError = viewModel.isPasswordError,
        passwordErrorText = viewModel.passwordErrorText,
        onValueChanged = {viewModel.onPasswordTextChanged(it)}
    )

    Spacer(modifier = Modifier.height(20.dp))

    LoginButton {
        viewModel.login(viewModel.phoneNumberText,viewModel.passwordText)
    }

}

@Composable
fun PhoneNumberTextField(
    value:String,
    isPhoneNumberError:Boolean,
    phoneNumberErrorText:String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Enter your phone number") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        value = value,
        isError = isPhoneNumberError,
        onValueChange = onValueChanged,
    )

    if (isPhoneNumberError) {
        Text(
            text = phoneNumberErrorText,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun PasswordTextField(
    value:String,
    isPasswordError:Boolean,
    passwordErrorText:String,
    onValueChanged: (String) -> Unit
){
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        visualTransformation = PasswordVisualTransformation(),
        value = value,
        isError = isPasswordError,
        onValueChange = onValueChanged,
    )

    if (isPasswordError) {
        Text(
            text = passwordErrorText,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun LoginButton(
    onButtonClicked: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(Color.Red)
            .clickable { onButtonClicked },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Continue", color = Color.White, modifier = Modifier.padding(16.dp))
        Image(
            painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "content description",
            modifier = Modifier.height(16.dp),
        )

    }
}