package com.example.roomdatabase.screens

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.roomdatabase.Database.Person
import com.example.roomdatabase.R
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
        val viewHolder = PersonListAdapter.PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent,false))
        return viewHolder
    }

    // present the data in the holder
    // gets updated when submitList is called
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
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
