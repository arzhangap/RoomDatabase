package com.example.roomdatabase.screens.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Person


// Use ListAdapter to use observable pattern.
class PersonListAdapter() : ListAdapter<Person, PersonListAdapter.PersonViewHolder>(DiffCallback) {
    // make a DiffCallBack object to be aware of the changes
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }

    // viewHolder for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val viewHolder = PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent,false))
        return viewHolder
    }

    // present the data in the holder
    // gets updated when submitList is called
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    // create the viewHolder
    class PersonViewHolder(item: View): RecyclerView.ViewHolder(item) {

        // get a reference to views
        val idText: TextView
        val nameText: TextView
        val ageText: TextView
        init {
            idText = item.findViewById(R.id.idNumber)
            nameText = item.findViewById(R.id.nameTextView)
            ageText = item.findViewById(R.id.ageTextView)
        }
        // create a bind function to update the holder when needed
        fun bind(person: Person) {
            idText.text = person.id.toString()
            nameText.text = person.name
            ageText.text = person.age
        }
    }
}
