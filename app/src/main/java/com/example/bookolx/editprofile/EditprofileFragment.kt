package com.example.bookolx.editprofile

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
import com.example.bookolx.booklist.BooklistFragmentArgs
import com.example.bookolx.booklist.BooklistViewModel
import com.example.bookolx.booklist.BooklistViewModelFactory
import com.example.bookolx.databinding.FragmentEditprofileBinding
import com.example.bookolx.home.HomeFragmentDirections

class EditprofileFragment : Fragment() {
    private lateinit var viewModel: EditprofileViewModel
    private lateinit var viewModelFactory: EditprofileViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEditprofileBinding>(inflater, R.layout.fragment_editprofile, container, false)

        viewModelFactory = EditprofileViewModelFactory(
            EditprofileFragmentArgs.fromBundle(requireArguments()).token!!,
            EditprofileFragmentArgs.fromBundle(requireArguments()).username!!,
            EditprofileFragmentArgs.fromBundle(requireArguments()).email!!,
            EditprofileFragmentArgs.fromBundle(requireArguments()).phone!!,
            EditprofileFragmentArgs.fromBundle(requireArguments()).password!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditprofileViewModel::class.java)
        binding.editprofileViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventEditprofileSuccess.observe(viewLifecycleOwner, { hasEditedSuccess ->
            if (hasEditedSuccess) {
                onEditComplete()
            }
        })

        viewModel.eventEditprofileFailed.observe(viewLifecycleOwner, { hasEditedFailed ->
            if (hasEditedFailed) {
                Toast.makeText(activity, "Edit failed", Toast.LENGTH_LONG).show()
                viewModel.onEditFailedComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun onEditComplete() {
        Log.i("EditprofileFragment", "" + viewModel.emailEdited.value + " " + EditprofileFragmentArgs.fromBundle(requireArguments()).email)
        if (viewModel.emailEdited.value == EditprofileFragmentArgs.fromBundle(requireArguments()).email) {
            val action = EditprofileFragmentDirections.actionEditprofileFragmentToHomeFragment(
                EditprofileFragmentArgs.fromBundle(requireArguments()).token!!,
                viewModel.emailEdited.value,
                viewModel.usernameEdited.value,
                EditprofileFragmentArgs.fromBundle(requireArguments()).password!!
            )

            NavHostFragment.findNavController(this).navigate(action)
        }
        else {
            val action = EditprofileFragmentDirections.actionEditprofileFragmentToLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.onEditSuccessComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = EditprofileFragmentDirections.actionEditprofileFragmentToAboutFragment()
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