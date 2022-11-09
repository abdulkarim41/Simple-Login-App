package com.abdulkarim.simpleloginapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abdulkarim.simpleloginapp.ui.theme.SimpleLoginAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val viewModel = remember { LoginViewModel() }

                    when(viewModel.uiState.collectAsState().value){
                        is LoginUiState.Loading -> LoadingScreen()
                        is LoginUiState.LoginSuccess -> {
                            Toast.makeText(this, (viewModel.uiState.collectAsState().value as LoginUiState.LoginSuccess).message, Toast.LENGTH_SHORT).show()
                        }
                        LoginUiState.InitialState -> LoginScreen(viewModel)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleLoginAppTheme {

    }
}


