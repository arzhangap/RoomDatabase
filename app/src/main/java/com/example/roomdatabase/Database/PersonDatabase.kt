package com.example.roomdatabase.Database

import android.content.Context
import android.database.DatabaseUtils
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase()  {

    abstract val personDao: PersonDao

    companion object {
        @Volatile
        var INSTANCE: PersonDatabase? = null

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