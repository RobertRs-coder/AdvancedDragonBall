package com.advanced.advanceddragonball.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.advanced.advanceddragonball.databinding.ActivityLoginBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        with(binding) {
            loginButton.setOnClickListener {
                val email: String = binding.userEmail.text.toString()
                val password: String = binding.userPassword.text.toString()
                viewModel.login(email, password)
            }
        }
    }

    private fun setObservers() {

    }
}