package com.example.bookolx.editprofile

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
import com.example.bookolx.booklist.BooklistFragmentArgs
import com.example.bookolx.booklist.BooklistViewModel
import com.example.bookolx.booklist.BooklistViewModelFactory
import com.example.bookolx.databinding.FragmentEditprofileBinding

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

        viewModel.eventEditprofileSuccess.observe(viewLifecycleOwner, { hasLoggedIn ->
            if (hasLoggedIn) {
                onEditComplete()
            }
        })

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
}