package com.advanced.advanceddragonball.ui.login

import com.advanced.advanceddragonball.domain.Hero

sealed class LoginState {
    data class Succcess(val token: String) : LoginState()
    data class Failure(val error: String?) : LoginState()
    data class NetworkError(val code: Int) : LoginState()
    object Loading: LoginState()
}