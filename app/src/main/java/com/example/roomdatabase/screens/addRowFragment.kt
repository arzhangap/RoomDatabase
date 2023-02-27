package com.example.roomdatabase.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentAddRowBinding

class addRowFragment : Fragment() {
    private var _binding: FragmentAddRowBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRowBinding.inflate(inflater)
        val view = binding.root
        val navController = findNavController()
        // Inflate the layout for this fragment
        return view
    }
}