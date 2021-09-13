package com.example.bookolx.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentRegisterBinding

// TODO add toast if http request is over
class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register, container, false)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventRegisterSuccess.observe(viewLifecycleOwner, { hasLoggedIn ->
            if (hasLoggedIn) {
                Register()
            }
        })

        viewModel.eventRegisterFailed.observe(viewLifecycleOwner, { hasFailed ->
            if (hasFailed) {
                RegisterFailed()
            }
        })

        return binding.root

    }

    private fun Register() {
        Log.i("RegisterViewModel", "register " + viewModel.emailRegister.value
                + " " + viewModel.usernameRegister.value + " " + viewModel.passwordRegister.value + " " + viewModel.phoneRegister.value)


        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onRegisterSuccessComplete()
    }

    private fun RegisterFailed() {
        Toast.makeText(activity, "Register failed", Toast.LENGTH_LONG).show()
        viewModel.onRegisterFailedComplete()
    }
}