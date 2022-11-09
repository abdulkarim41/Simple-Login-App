package com.abdulkarim.simpleloginapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun login(){
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
