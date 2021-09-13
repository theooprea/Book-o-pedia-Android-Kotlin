package com.example.bookolx.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookolx.BooklistAdapter
import com.example.bookolx.R
import com.example.bookolx.WishlistAdapter
import com.example.bookolx.booklist.BooklistFragmentArgs
import com.example.bookolx.booklist.BooklistViewModel
import com.example.bookolx.booklist.BooklistViewModelFactory
import com.example.bookolx.databinding.FragmentBooklistBinding
import com.example.bookolx.databinding.FragmentWishlistBinding
import com.example.bookolx.home.HomeFragmentDirections

class WishlistFragment : Fragment() {

    private lateinit var viewModel: WishlistViewModel
    private lateinit var viewModelFactory: WishlistViewModelFactory
    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>

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

        booksRecyclerView = binding.recyclerView2
        booksRecyclerView.layoutManager = LinearLayoutManager(context)
        booksRecyclerView.setHasFixedSize(true)

        adapter = WishlistAdapter(viewModel.booksArrayList, viewModel.token, viewModel.username, this)

        booksRecyclerView.adapter = adapter

        viewModel.getData()

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, {
            if (it) {
                adapter.notifyDataSetChanged()
                viewModel.getDataSuccessComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = WishlistFragmentDirections.actionWishlistFragmentToAboutFragment()
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