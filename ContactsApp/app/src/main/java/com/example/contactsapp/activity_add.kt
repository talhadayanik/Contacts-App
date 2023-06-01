package com.example.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import com.example.contactsapp.models.Contact
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class activity_add : AppCompatActivity() {

    lateinit var txtName : EditText
    lateinit var txtNumber : EditText
    lateinit var txtAddress : EditText
    lateinit var btnGroup : Button
    lateinit var btnAdd : Button
    var selectedGroup : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        txtName = findViewById(R.id.txtAddName)
        txtNumber = findViewById(R.id.txtAddNum)
        txtAddress = findViewById(R.id.txtAddAddress)
        btnGroup = findViewById(R.id.btnAddGroup)
        btnAdd = findViewById(R.id.btnAdd)

        val groupPopupMenu = PopupMenu(this, btnGroup, Gravity.BOTTOM)
        groupPopupMenu.menu.add("Family")
        groupPopupMenu.menu.add("Friends")
        groupPopupMenu.menu.add("Colleagues")

        btnGroup.setOnClickListener {
            groupPopupMenu.show()
            groupPopupMenu.setOnMenuItemClickListener {
                selectedGroup = it.toString()
                btnGroup.text = selectedGroup
                false
            }
        }

        btnAdd.setOnClickListener {
            if(txtName.text.isNotEmpty() && txtNumber.text.isNotEmpty() && txtAddress.text.isNotEmpty() && selectedGroup.isNotEmpty()){
                val contact = Contact(null, selectedGroup, txtName.text.toString(), txtNumber.text.toString(), txtAddress.text.toString())
                GlobalScope.launch(Dispatchers.IO){
                    MainActivity.db.contactDao().insertContact(contact)
                }
                finish()
            }else{
                Snackbar.make(it, "Fields cannot be empty", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}