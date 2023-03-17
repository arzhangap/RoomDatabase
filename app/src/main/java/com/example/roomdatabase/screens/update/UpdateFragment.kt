package com.example.roomdatabase.screens.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Person
import com.example.roomdatabase.viewmodel.UpdateViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var viewModel: UpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        val personName = view.findViewById<TextView>(R.id.updateTextPersonName)
        val personAge = view.findViewById<TextView>(R.id.updateTextPersonAge)
        val updateBtn = view.findViewById<TextView>(R.id.updateButton)
        personName.setText(args.selectedPerson.name)
        personAge.setText(args.selectedPerson.age)

        val navController = findNavController()

        viewModel = ViewModelProvider(this)[UpdateViewModel::class.java]

        updateBtn.setOnClickListener {
            val person = Person(args.selectedPerson.id, personName.text.toString(), personAge.text.toString())
            viewModel.update(person)
            if(viewModel.isInputValid) {
                navController.navigateUp()
                viewModel.onUpdateComplete()
            } else {
                Toast.makeText(requireContext(), "Please fill in the blanks correctly!",Toast.LENGTH_LONG).show()
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
                   deletePerson()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    // handle the delete
    private fun deletePerson() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _,_ ->
            viewModel.deletePerson(args.selectedPerson)
            Toast.makeText(requireContext(),"Deleted Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete ${args.selectedPerson.name}")
        builder.setMessage("Are you sure you want to delete ${args.selectedPerson.name}?")
        builder.create().show()
    }
}
