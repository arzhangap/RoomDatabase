package com.example.roomdatabase.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding

class listFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        val view = binding.root

        // set add click listener
        binding.addDataButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listFragment_to_addRowFragmentaf))

        // Inflate the layout for this fragment
        return view
    }
}