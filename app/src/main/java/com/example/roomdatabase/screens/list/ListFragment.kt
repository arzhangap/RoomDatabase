package com.example.roomdatabase.screens.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import com.example.roomdatabase.viewmodel.ListViewModel
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.readAllData().collect { person ->
                    adapter.submitList(person)
                }
            }
        }

        val menuHost: MenuHost = requireActivity()

        // setHasOptionsMenu is deprecated so we use this instead
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.item_popup_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId == R.id.menu_delete) {
                    deleteAll()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Inflate the layout for this fragment
        return view
    }

    fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _,_ ->
            viewModel.deleteAll()
            Toast.makeText(requireContext(),"Deleted Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete All Persons.")
        builder.setMessage("Are you sure you want to delete all persons?")
        builder.create().show()
    }
}