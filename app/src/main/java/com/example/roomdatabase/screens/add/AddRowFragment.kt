package com.example.roomdatabase.screens.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.databinding.FragmentAddRowBinding
import com.example.roomdatabase.model.Person
import com.example.roomdatabase.viewmodel.AddRowViewModel

class AddRowFragment : Fragment() {

    private var _binding: FragmentAddRowBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: AddRowViewModel

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