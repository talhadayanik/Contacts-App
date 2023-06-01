package com.example.contactsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.dao.ContactDao
import com.example.contactsapp.models.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ContactDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}