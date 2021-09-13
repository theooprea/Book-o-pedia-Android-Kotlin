package com.example.bookolx.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
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

        viewModel.eventDataFailed.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(activity, "Error in getting data, please re-login", Toast.LENGTH_LONG).show()

                viewModel.getDataFailed()
            }
        })

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = HomeFragmentDirections.actionHomeFragmentToAboutFragment()
            NavHostFragment.findNavController(this).navigate(action)
            return true
        }
        else if (item.toString() == "Log out") {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
                super.onOptionsItemSelected(item)
        }
        else {
            return false
        }
    }
}