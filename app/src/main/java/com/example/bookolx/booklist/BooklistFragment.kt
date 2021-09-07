package com.example.bookolx.booklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookolx.BooklistAdapter
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentBooklistBinding

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

        viewModelFactory = BooklistViewModelFactory(BooklistFragmentArgs.fromBundle(requireArguments()).token!!,
            BooklistFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(BooklistViewModel::class.java)

        booksRecyclerView = binding.recyclerView
        booksRecyclerView.layoutManager = LinearLayoutManager(context)
        booksRecyclerView.setHasFixedSize(true)

        adapter = BooklistAdapter(viewModel.booksArrayList)

        booksRecyclerView.adapter = adapter

        viewModel.getData()

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, {
            if (it) {
                adapter.notifyDataSetChanged()
                viewModel.getDataComplete()
            }
        })

        return binding.root
    }
}