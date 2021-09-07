package com.example.bookolx.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
            EditprofileFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditprofileViewModel::class.java)

        return binding.root
    }
}