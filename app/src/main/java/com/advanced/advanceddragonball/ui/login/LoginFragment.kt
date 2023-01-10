package com.advanced.advanceddragonball.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.advanced.advanceddragonball.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.state.observe(viewLifecycleOwner) { it ->
            when (it) {
                is LoginState.Success -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHeroListFragment())
                }
                is LoginState.Failure -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }
                is LoginState.NetworkError -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.code.toString(), Toast.LENGTH_LONG).show()
                }
                is LoginState.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}