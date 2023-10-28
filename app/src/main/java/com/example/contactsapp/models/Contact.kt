package com.example.contactsapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int?,

    @ColumnInfo(name = "group")
    val group: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "number")
    val number: String?,

    @ColumnInfo(name = "address")
    val address: String?
)
