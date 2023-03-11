package com.example.roomdatabase.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.Database.Person


import com.example.roomdatabase.databinding.FragmentAddRowBinding

private lateinit var viewModel: AddRowViewModel

class AddRowFragment : Fragment() {
    private var _binding: FragmentAddRowBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRowBinding.inflate(inflater)

        val view = binding.root

        // get a refernce of the viewModel
        viewModel = ViewModelProvider(this)[(AddRowViewModel::class.java)]

        //find navController
        val navController = findNavController()

        // Set up add to database button
        binding.addButton.setOnClickListener {
        val name = binding.editTextPersonName.text.toString()
        val age = binding.editTextPersonAge.text.toString()
            val person = Person(0,name, age)
            viewModel.insert(person)
            navController.navigateUp()
        }
        // Inflate the layout for this fragment
        return view
    }
}