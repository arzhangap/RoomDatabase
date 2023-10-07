package com.example.roomdatabase.screens.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import com.example.roomdatabase.viewmodel.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel

    private val adapter: PersonListAdapter by lazy { PersonListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        val view = binding.root

        // Navigate to AddRowFragment
        binding.addDataButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listFragment_to_addRowFragment))

        // Set Up RecyclerView
        val recyclerView = binding.personList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //viewModel
        viewModel = ViewModelProvider(this)[(ListViewModel::class.java)]

        // read all data
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

                val search = menu.findItem(R.id.search)
                val searchView = search.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                search.isVisible = true
                searchView.setOnQueryTextListener(this@ListFragment)
                val delete = menu.findItem(R.id.menu_delete)

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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchList(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null) {
            searchList(newText)
        }
        return true
    }

    private fun searchList(query: String) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.searchDatabase(query).collect {
                adapter.submitList(it)
            }
        }

    }

}