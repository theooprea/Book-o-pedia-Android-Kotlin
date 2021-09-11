package com.example.bookolx.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)

        viewModelFactory = HomeViewModelFactory(HomeFragmentArgs.fromBundle(requireArguments()).token!!,
            HomeFragmentArgs.fromBundle(requireArguments()).email!!,
            HomeFragmentArgs.fromBundle(requireArguments()).username!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.homeFragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getData()

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, {
            if (it) {
                binding.phoneTextView.text = viewModel.phone.value
                binding.usernameTextview.text = viewModel.username.value
                binding.emailTextView.text = viewModel.email.value

                viewModel.getDataComplete()
            }
        })

        return binding.root
    }

    fun editProfile() {
        val action = HomeFragmentDirections.actionHomeFragmentToEditprofileFragment(viewModel._token,
            viewModel.username.value, viewModel.email.value, viewModel.phone.value,
            HomeFragmentArgs.fromBundle(requireArguments()).password!!)
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun bookList() {
        val action = HomeFragmentDirections.actionHomeFragmentToBooklistFragment(viewModel._token, viewModel.username.value)
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun wishList() {
        val action = HomeFragmentDirections.actionHomeFragmentToWishlistFragment(viewModel._token, viewModel.username.value)
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun search() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(viewModel._token, viewModel.username.value)
        NavHostFragment.findNavController(this).navigate(action)
    }
}