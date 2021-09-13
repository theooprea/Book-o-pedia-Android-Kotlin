package com.example.bookolx.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bookolx.R
import com.example.bookolx.databinding.FragmentAboutBinding

class About : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAboutBinding>(inflater, R.layout.fragment_about, container, false)

        return binding.root
    }
}