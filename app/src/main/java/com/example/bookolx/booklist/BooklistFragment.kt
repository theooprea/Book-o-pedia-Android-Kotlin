package com.example.bookolx.booklist

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
import com.example.bookolx.addbook.AddbookFragment
import com.example.bookolx.databinding.FragmentBooklistBinding
import com.example.bookolx.home.HomeFragmentDirections

class BooklistFragment : Fragment() {
    private lateinit var viewModel: BooklistViewModel
    private lateinit var viewModelFactory: BooklistViewModelFactory
    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<BooklistAdapter.BooklistViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentBooklistBinding>(inflater, R.layout.fragment_booklist, container, false)
        binding.booklistFragment = this

        viewModelFactory = BooklistViewModelFactory(BooklistFragmentArgs.fromBundle(requireArguments()).token!!,
            BooklistFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(BooklistViewModel::class.java)

        booksRecyclerView = binding.recyclerView
        booksRecyclerView.layoutManager = LinearLayoutManager(context)
        booksRecyclerView.setHasFixedSize(true)

        adapter = BooklistAdapter(viewModel.booksArrayList, viewModel.token, viewModel.username, this)

        booksRecyclerView.adapter = adapter

        viewModel.getData()

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, {
            if (it) {
                adapter.notifyDataSetChanged()
                viewModel.getDataComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    fun onAddBook() {
        val action = BooklistFragmentDirections.actionBooklistFragmentToAddbookFragment(BooklistFragmentArgs.fromBundle(requireArguments()).token,
            BooklistFragmentArgs.fromBundle(requireArguments()).username)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "About") {
            val action = BooklistFragmentDirections.actionBooklistFragmentToAboutFragment()
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