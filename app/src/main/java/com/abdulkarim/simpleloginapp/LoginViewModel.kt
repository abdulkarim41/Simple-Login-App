package com.abdulkarim.simpleloginapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {

    // phone number state
    var phoneNumberText by mutableStateOf("")
    var isPhoneNumberError by mutableStateOf(false)
    var phoneNumberErrorText by mutableStateOf("")

    // password state
    var passwordText by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var passwordErrorText by mutableStateOf("")

    // phone number and password event
    fun onPhoneNumberTextChanged(newString: String) {
        if (newString.length <= 11) phoneNumberText = newString
    }
    fun onPasswordTextChanged(newString: String) {
        if (newString.length <= 6) phoneNumberText = newString
        passwordText = newString
    }

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun login(phoneNumber:String,password:String){

        if (phoneNumber.isEmpty()){
            phoneNumberErrorText = "Please enter phone number"
            isPhoneNumberError = true
            return
        }
        if ((!Pattern.compile("((0|01|\\+88|\\+88\\s*\\(0\\)|\\+88\\s*0)\\s*)?1(\\s*[0-9]){9}").matcher(phoneNumber).matches())){
            phoneNumberErrorText = "Please input your valid phone number"
            isPhoneNumberError = true
            return
        }
        if (password.isEmpty()){
            passwordErrorText = "Please enter your password"
            isPasswordError = true
        }
        _uiState.value = LoginUiState.Loading(true)
        viewModelScope.launch {
            delay(500)
            _uiState.value = LoginUiState.LoginSuccess("Login success")
        }
    }

}

sealed class LoginUiState{
    data class Loading(val loading: Boolean) : LoginUiState()
    data class LoginSuccess(val message:String) : LoginUiState()
    object InitialState : LoginUiState()
}
