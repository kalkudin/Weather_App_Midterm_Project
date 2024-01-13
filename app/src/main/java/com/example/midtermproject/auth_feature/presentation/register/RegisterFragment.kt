package com.example.midtermproject.auth_feature.presentation.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.databinding.FragmentRegisterLayoutBinding
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterLayoutBinding>(FragmentRegisterLayoutBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindRegisterButton()
        bindAlreadyRegisteredButton()
        bindBackButton()
    }

    override fun bindObservers() {
        bindNavigationEvents()
        bindRegistrationFlow()
    }

    private fun bindRegisterButton() {
        with(binding) {
            btnRegister.setOnClickListener {
                registerViewModel.onEvent(
                    RegisterEvent.Register(
                        email = etEmail.text.toString(),
                        password = etPassword.text.toString(),
                        repeatPassword = etRepeatPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun bindAlreadyRegisteredButton(){
        with(binding) {
            btnAlreadyRegistered.setOnClickListener {
                registerViewModel.onEvent(RegisterEvent.UserAlreadyAuthenticated)
            }
        }
    }

    private fun bindBackButton(){
        with(binding) {
            btnBack.setOnClickListener {
                registerViewModel.onEvent(RegisterEvent.TakeUserToHome)
            }
        }
    }

    private fun bindRegistrationFlow(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.registerFlow.collect {
                    when(it){
                        is Resource.Success -> {
                            hideProgressBar()
                            showSuccess()
                        }
                        is Resource.Error -> {
                            hideProgressBar()
                            showError(it.errorMessage)
                        }
                        is Resource.Loading -> {
                            showProgressBar()
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun bindNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.navigationFlow.collect { event ->
                    when (event) {
                        is RegisterNavigationEvent.NavigateToLogin -> navigateToLogin()
                        is RegisterNavigationEvent.NavigateToHome -> navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun showSuccess(){
        Snackbar.make(binding.root, "Registration successful", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun showProgressBar(){
        with(binding){
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar(){
        with(binding){
            progressBar.visibility = View.GONE
        }
    }
}