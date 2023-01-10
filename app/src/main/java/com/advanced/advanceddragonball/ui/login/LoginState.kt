package com.advanced.advanceddragonball.ui.login

sealed class LoginState {
    data class Success(val token: String) : LoginState()
    data class Failure(val error: String?) : LoginState()
    data class NetworkError(val code: Int) : LoginState()
    object Loading: LoginState()
}