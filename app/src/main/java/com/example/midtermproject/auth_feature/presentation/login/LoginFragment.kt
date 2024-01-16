package com.example.midtermproject.auth_feature.presentation.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.auth_feature.presentation.event.LoginEvent
import com.example.midtermproject.auth_feature.presentation.model.AuthState
import com.example.midtermproject.databinding.FragmentLoginLayoutBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginLayoutBinding>(FragmentLoginLayoutBinding::inflate) {

    private val loginViewModel : LoginViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindLoginBtn()
        bindSignUpHere()
        bindBackButton()
    }

    override fun bindObservers() {
        bindLoginFlow()
        bindNavigationEvents()
    }

    private fun bindLoginBtn() {
        with(binding) {
            btnLogin.setOnClickListener {
                if(btnRememberMe.isChecked) {
                    loginViewModel.onEvent(LoginEvent.LoginAndRememberUser(email = etEmail.text.toString(), password = etPassword.text.toString()))
                    return@setOnClickListener
                }
                loginViewModel.onEvent(LoginEvent.LoginUser(email = etEmail.text.toString(), password = etPassword.text.toString()))
            }
        }
    }

    private fun bindSignUpHere() {
        with(binding) {
            btnNotRegistered.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.UserNotAuthenticatedYet)
            }
        }
    }

    private fun bindBackButton(){
        with(binding) {
            btnBack.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.TakeUserToHome)
            }
        }
    }

    private fun bindLoginFlow(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFlow.collect { state ->
                    handleLoginState(state)
                    loginViewModel.onEvent(LoginEvent.ResetFlow)
                }
            }
        }
    }

    private fun bindNavigationEvents(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.navigationFlow.collect {
                    when(it){
                        is LoginNavigationEvent.NavigateUserToHome -> navigateToHome()
                        is LoginNavigationEvent.NavigateUserToRegister -> navigateToRegister()
                        is LoginNavigationEvent.NavigateUserToWeather -> navigateToWeather()
                    }
                }
            }
        }
    }

    private fun handleLoginState(state: AuthState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.isSuccess) {
            showSuccess()
        }

        state.errorMessage?.let { errorMessage ->
            showError(errorMessage)
        }
    }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navigateToRegister(){
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToWeather(){
        findNavController().navigate(R.id.action_loginFragment_to_weatherTodayFragment)
    }

    private fun showSuccess(){
        Snackbar.make(binding.root, "Login Success", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
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