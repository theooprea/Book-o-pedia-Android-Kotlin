package com.example.bookolx.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventLoginSuccess.observe(viewLifecycleOwner, { hasLoggedIn ->
            if (hasLoggedIn) {
                Login()
            }
         })

        binding.registerButton.setOnClickListener {
            Register() }

        return binding.root
    }

    private fun Login() {
        Log.i("LoginFragment", "login " + viewModel.emailLogin.value + " " + viewModel.passwordLogin.value)

        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onLoginSuccessComplete()
    }

    private fun Register() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}