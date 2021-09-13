package com.example.bookolx.addbook

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentAddbookBinding
import com.example.bookolx.home.HomeFragmentDirections

class AddbookFragment : Fragment() {
    private lateinit var viewModel: AddbookViewModel
    private lateinit var viewModelFactory: AddbookViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddbookBinding>(inflater, R.layout.fragment_addbook, container, false)

        viewModelFactory = AddbookViewModelFactory(
            AddbookFragmentArgs.fromBundle(requireArguments()).token!!,
            AddbookFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddbookViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.addbookViewModel = viewModel

        viewModel.eventCreatedSuccess.observe(viewLifecycleOwner, {
            if (it) {
                bookCreated()
            }
        })

        viewModel.eventCreatedFailed.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(activity, "Failed to add book", Toast.LENGTH_LONG).show()
                viewModel.onBookCreatedFailedComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    fun bookCreated() {
        val action = AddbookFragmentDirections.actionAddbookFragmentToBooklistFragment(
            AddbookFragmentArgs.fromBundle(requireArguments()).token!!,
            AddbookFragmentArgs.fromBundle(requireArguments()).username!!
        )

        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onBookCreatedSuccessComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = AddbookFragmentDirections.actionAddbookFragmentToAboutFragment()
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