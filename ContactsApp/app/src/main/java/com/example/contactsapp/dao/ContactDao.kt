package com.example.contactsapp.dao

import androidx.room.*
import com.example.contactsapp.models.Contact

@Dao
interface ContactDao {
    @Insert
    fun insertContact(contact: Contact) : Long

    @Query("select * from contacts where id =:id")
    fun getContactById(id: Int) : Contact

    @Delete
    fun deleteContact(contact: Contact) : Int

    @Update
    fun updateContact(contact: Contact) : Int

    @Query("select * from contacts order by id desc limit 10")
    fun getLastTenContact(): List<Contact>

    @Query("select * from contacts where name like '%' || :searchTerm || '%'")
    fun searchByName(searchTerm: String) : List<Contact>

    @Query("select * from contacts where `group` = :cGroup")
    fun getDataByGroup(cGroup: String) : List<Contact>
}