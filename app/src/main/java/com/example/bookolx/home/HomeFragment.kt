package com.example.bookolx.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        viewModel.getData()

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.phoneTextView.text = viewModel.phone.value
                binding.usernameTextview.text = viewModel.username.value
                binding.emailTextView.text = viewModel.email.value

                viewModel.getDataComplete()
            }
        })

        return binding.root
    }
}