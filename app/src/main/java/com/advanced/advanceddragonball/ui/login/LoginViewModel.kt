package com.advanced.advanceddragonball.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val repository: Repository
): ViewModel() {
    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() =_state

    companion object {
        private const val CREDENTIALS = "cnJvam8udmFAZ21haWwuY29tOjEyMzQ1Ng=="
        private const val TAG = "LoginViewModel: "
    }

    fun login(email: String, password: String) {
        if(emailIsValid(email) && passwordIsValid(password)) {
            viewModelScope.launch {
                val loginState = withContext(Dispatchers.IO) {
                    //TODO: Repository getToken(email, password)
                    repository.login(email, password)
                }
                _state.value = loginState
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
}