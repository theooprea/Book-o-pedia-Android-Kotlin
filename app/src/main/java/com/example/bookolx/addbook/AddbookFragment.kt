package com.example.bookolx.addbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentAddbookBinding

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
}