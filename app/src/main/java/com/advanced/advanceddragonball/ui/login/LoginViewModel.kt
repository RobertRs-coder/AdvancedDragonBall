package com.advanced.advanceddragonball.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.data.local.datastore.PrefsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val repository: Repository,
): ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() =_state

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean>
        get() =_loginState

    companion object {
        private const val TAG = "LoginViewModel: "
    }

    fun login(email: String, password: String) {
        if(emailIsValid(email) && passwordIsValid(password)) {
            viewModelScope.launch {
                val loginState = withContext(Dispatchers.IO) {
                    repository.login(email, password)
                }
                _state.value = loginState
                Log.d("TOKEN", "token ViewModel ${_state.value.toString()}")
            }
        }
    }

    private fun emailIsValid(email: String): Boolean {
        if (email.isEmpty() || email.isBlank()){
            return false
        }
        return true
    }

    private fun passwordIsValid(password: String): Boolean {
        if (password.isEmpty() || password.isBlank()){
            return false
        }
        return true
    }
    fun tokenIsEmpty() {
        viewModelScope.launch {
            val tokenState = withContext(Dispatchers.IO) {
                val token = repository.getToken()

                return@withContext token?.isEmpty() == true
            }

            _loginState.value = tokenState
        }
    }
}