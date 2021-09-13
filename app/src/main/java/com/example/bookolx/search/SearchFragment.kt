package com.example.bookolx.search

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookolx.Book
import com.example.bookolx.R
import com.example.bookolx.SeachAdapter
import com.example.bookolx.databinding.FragmentSearchBinding
import com.example.bookolx.home.HomeFragmentDirections

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<SeachAdapter.SearchViewHolder>
    private lateinit var binding: FragmentSearchBinding
    private var visibility = false

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false)

        viewModelFactory = SearchViewModelFactory(
            SearchFragmentArgs.fromBundle(requireArguments()).token!!,
            SearchFragmentArgs.fromBundle(requireArguments()).username!!)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        binding.searchFragment = this
        binding.searchViewModel = viewModel

        searchRecyclerView = binding.searchRecycleView
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerView.setHasFixedSize(true)

        adapter = SeachAdapter(viewModel.booksArrayList, viewModel.token, viewModel.username, this)

        searchRecyclerView.adapter = adapter

        viewModel.getData(viewModel.titleInput.value, viewModel.genreInput.value, compareBy { it.price })

        viewModel.eventDataSuccess.observe(viewLifecycleOwner, {
            if (it) {
                Log.i("SearchFragment", "notified")
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
            val action = SearchFragmentDirections.actionSearchFragmentToAboutFragment()
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

    fun onMoreOptions () {
        val radioGroup = binding.searchRadioGroup
        val genreInput = binding.searchGenre
        val recyclerView = binding.searchRecycleView
        val params = (recyclerView.layoutParams as ViewGroup.MarginLayoutParams)

        visibility = !visibility

        Log.i("SearchFragment", visibility.toString())

        if (visibility) {
            radioGroup.visibility = View.VISIBLE
            genreInput.visibility = View.VISIBLE
            params.setMargins(0, 200.px, 0, 0)
        }
        else {
            radioGroup.visibility = View.GONE
            genreInput.visibility = View.GONE
            params.setMargins(0, 96.px, 0, 0)
        }
    }

    fun onSearch() {
        val radioGroup = binding.searchRadioGroup

        Log.i("SearchFragment", "On search " + radioGroup.checkedRadioButtonId)
        if (radioGroup.checkedRadioButtonId == R.id.radioButton) {
            viewModel.getData(viewModel.titleInput.value, viewModel.genreInput.value, compareBy { it.price })
        }
        else if (radioGroup.checkedRadioButtonId == R.id.radioButton2) {
            viewModel.getData(viewModel.titleInput.value, viewModel.genreInput.value, compareByDescending { it.price })
        }
    }
}