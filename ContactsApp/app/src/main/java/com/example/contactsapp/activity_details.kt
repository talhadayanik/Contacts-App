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

class activity_details : AppCompatActivity() {

    lateinit var txtName: EditText
    lateinit var txtNumber: EditText
    lateinit var txtAddress: EditText
    lateinit var btnGroup: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    var selectedGroup : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        txtName = findViewById(R.id.txtDetailsName)
        txtNumber = findViewById(R.id.txtDetailsNum)
        txtAddress = findViewById(R.id.txtDetailsAddress)
        btnGroup = findViewById(R.id.btnDetailsGroup)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)


        txtName.setText(MainActivity.selectedContact?.name)
        txtNumber.setText(MainActivity.selectedContact?.number)
        txtAddress.setText(MainActivity.selectedContact?.address)
        btnGroup.setText(MainActivity.selectedContact?.group)
        selectedGroup = MainActivity.selectedContact?.group

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

        btnUpdate.setOnClickListener {
            if(txtName.text.isNotEmpty() && txtNumber.text.isNotEmpty() && txtAddress.text.isNotEmpty() && selectedGroup!!.isNotEmpty()){
                val contact = Contact(MainActivity.selectedContact!!.id, selectedGroup, txtName.text.toString(), txtNumber.text.toString(), txtAddress.text.toString())
                GlobalScope.launch(Dispatchers.IO){
                    MainActivity.db.contactDao().updateContact(contact)
                }
                finish()
            }else{
                Snackbar.make(it, "Fields cannot be empty", Snackbar.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                MainActivity.db.contactDao().deleteContact(MainActivity.selectedContact!!)
            }
            finish()
        }
    }
}