package com.example.midtermproject.presentation.auth_feature.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.databinding.FragmentHomeLayoutBinding
import com.example.midtermproject.presentation.auth_feature.event.HomeEvent
import com.example.midtermproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeLayoutBinding>(FragmentHomeLayoutBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindLoginBtn()
        bindRegisterBtn()
    }

    override fun bindObservers() {
        bindNavigationEvents()
    }

    private fun bindLoginBtn() {
        binding.btnLogin.setOnClickListener {
            homeViewModel.onEvent(HomeEvent.NavigateToLogin)
        }
    }

    private fun bindRegisterBtn() {
        binding.btnRegister.setOnClickListener {
            homeViewModel.onEvent(HomeEvent.NavigateToRegister)
        }
    }

    private fun bindNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.navigationFlow.collect { event ->
                    when (event) {
                        is HomeNavigationEvent.NavigateToLogin -> navigateToLogin()
                        is HomeNavigationEvent.NavigateToRegister -> navigateToRegister()
                        is HomeNavigationEvent.NavigateToWeather -> navigateToWeather()
                        is HomeNavigationEvent.RemainOnCurrentPage -> remainOnCurrentPage()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
    }

    private fun navigateToWeather() {
        findNavController().navigate(R.id.action_homeFragment_to_weatherTodayFragment)
    }

    private fun remainOnCurrentPage() {
    }
}