package com.example.bookolx.wishlist

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
import com.example.bookolx.databinding.FragmentBooklistBinding
import com.example.bookolx.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    private lateinit var viewModel: WishlistViewModel
    private lateinit var viewModelFactory: WishlistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentWishlistBinding>(inflater, R.layout.fragment_wishlist, container, false)

        viewModelFactory = WishlistViewModelFactory(
            WishlistFragmentArgs.fromBundle(requireArguments()).token!!,
            WishlistFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WishlistViewModel::class.java)

        return binding.root
    }

}