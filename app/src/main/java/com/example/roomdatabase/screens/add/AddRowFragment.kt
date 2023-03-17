package com.example.roomdatabase.screens.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.model.Person


import com.example.roomdatabase.databinding.FragmentAddRowBinding
import com.example.roomdatabase.viewmodel.AddRowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            // navigate back only if textEdits are full.
            if(viewModel.isInputValid) {
                navController.navigateUp()
                viewModel.onInsertComplete()
            } else {
                Toast.makeText(requireContext(), "Please fill in the blanks correctly!",Toast.LENGTH_LONG).show()
            }
        }
        // Inflate the layout for this fragment
        return view
    }
}