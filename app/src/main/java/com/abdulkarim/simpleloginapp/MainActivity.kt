package com.abdulkarim.simpleloginapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdulkarim.simpleloginapp.ui.theme.SimpleLoginAppTheme
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel:LoginViewModel = viewModel()

                    when(viewModel.uiState){
                        is LoginUiState.Loading -> LoadingScreen()
                        is LoginUiState.LoginSuccess -> Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                        LoginUiState.InitialState -> LoginScreen()
                    }

                }
            }
        }
    }

}

sealed class LoginUiState{
    data class Loading(val loading: Boolean) : LoginUiState()
    data class LoginSuccess(val message:String) : LoginUiState()
    object InitialState : LoginUiState()
}

class LoginViewModel() : ViewModel() {
    val uiState by mutableStateOf<LoginUiState>(LoginUiState.InitialState)
}

@Composable
fun LoginScreen() {
    val phoneNumber = remember { mutableStateOf("") }
    val maxInputNumber = 11
    val isError = remember { mutableStateOf(false) }
    val phoneNumberErrorText = remember { mutableStateOf("") }

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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter your phone number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            value = phoneNumber.value,
            isError = isError.value,
            onValueChange = {
                if (it.length <= maxInputNumber) phoneNumber.value = it
                isError.value = false
            },
        )

        if (isError.value) {
            Text(
                text = phoneNumberErrorText.value,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(Color.Red)
                .clickable {
                    if (phoneNumber.value.isEmpty()) {
                        phoneNumberErrorText.value = "Please input your phone number"
                        isError.value = true
                        return@clickable
                    }
                    if (!Pattern
                            .compile("((0|01|\\+88|\\+88\\s*\\(0\\)|\\+88\\s*0)\\s*)?1(\\s*[0-9]){9}")
                            .matcher(phoneNumber.value)
                            .matches()
                    ) {
                        phoneNumberErrorText.value = "Please input your valid phone number"
                        isError.value = true
                        return@clickable
                    }

                },
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

}

@Composable
fun LoadingScreen() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = Color.Red,
            strokeWidth = 4f.dp
        )

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleLoginAppTheme {

    }
}


