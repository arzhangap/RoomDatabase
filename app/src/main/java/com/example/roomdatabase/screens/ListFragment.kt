package com.example.roomdatabase.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        val view = binding.root

        // Navigate to AddRowFragment
        binding.addDataButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listFragment_to_addRowFragment))

        // Set Up RecyclerView
        val adapter = PersonListAdapter()
        val recyclerView = binding.personList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //viewModel
        viewModel = ViewModelProvider(this)[(ListViewModel::class.java)]

        lifecycle.coroutineScope.launch {
            viewModel.readAllData().collect {person ->
                adapter.submitList(person)
            }
        }

        // Inflate the layout for this fragment
        return view
    }
}