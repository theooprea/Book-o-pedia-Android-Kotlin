package com.example.bookolx.editbook

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
import com.example.bookolx.addbook.AddbookFragmentArgs
import com.example.bookolx.databinding.FragmentEditbookBinding
import com.example.bookolx.home.HomeFragmentDirections

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

        viewModel.eventEditedFailed.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(activity, "Edit Failed", Toast.LENGTH_LONG).show()
                viewModel.onBookEditedFailedComplete()
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.editbookViewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    fun bookEdited() {
        val action = EditbookFragmentDirections.actionEditbookFragmentToBooklistFragment(
            EditbookFragmentArgs.fromBundle(requireArguments()).token!!,
            EditbookFragmentArgs.fromBundle(requireArguments()).username!!)


        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onBookEditedSuccessComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = EditbookFragmentDirections.actionEditbookFragmentToAboutFragment()
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