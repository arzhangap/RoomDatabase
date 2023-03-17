package com.example.roomdatabase.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.model.Person

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase()  {

    abstract val personDao: PersonDao

    companion object {
        @Volatile
        var INSTANCE: PersonDatabase? = null

        // Get a reference of database if the INSTANCE is null.
        fun getDatabase(context: Context) : PersonDatabase {
           return INSTANCE ?: synchronized(this) {
               val instance: PersonDatabase = Room.databaseBuilder(
                   context.applicationContext,
                   PersonDatabase::class.java,
                   "person_database"
               ).fallbackToDestructiveMigration().build()
               instance
           }
        }
    }

}