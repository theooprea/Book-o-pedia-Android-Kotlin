package com.example.bookolx.editbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.bookolx.R
import com.example.bookolx.addbook.AddbookFragmentArgs
import com.example.bookolx.databinding.FragmentEditbookBinding

class EditbookFragment : Fragment() {
    private lateinit var viewModel: EditbookViewModel
    private lateinit var viewModelFactory: EditbookViewModelFactory
    private lateinit var binding: FragmentEditbookBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEditbookBinding>(inflater, R.layout.fragment_editbook, container, false)

        viewModelFactory = EditbookViewModelFactory(
            EditbookFragmentArgs.fromBundle(requireArguments()).token!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).username!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).title!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).author!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).genre!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).pages,
            EditbookFragmentArgs.fromBundle(requireArguments()).price,
            EditbookFragmentArgs.fromBundle(requireArguments()).quantity)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditbookViewModel::class.java)

        viewModel.eventEditedSuccess.observe(viewLifecycleOwner, {
            if (it) {
                bookEdited()
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.editbookViewModel = viewModel

        return binding.root
    }

    fun bookEdited() {
        val action = EditbookFragmentDirections.actionEditbookFragmentToBooklistFragment(
            EditbookFragmentArgs.fromBundle(requireArguments()).token!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).username!!)


        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onBookEditedSuccessComplete()
    }
}