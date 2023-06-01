package com.example.contactsapp.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contactsapp.R
import com.example.contactsapp.models.Contact

class ContactAdapter(private val context: Context, private var list: MutableList<Contact>) : ArrayAdapter<Contact>(context, R.layout.custom_contact_item_layout, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.custom_contact_item_layout, null, true)

        val name = view.findViewById<TextView>(R.id.txtItemName)
        val number = view.findViewById<TextView>(R.id.txtItemNumber)
        val address = view.findViewById<TextView>(R.id.txtItemAddress)
        val group = view.findViewById<TextView>(R.id.txtItemGroup)

        val contact = list.get(position)

        name.text = "${contact.name}"
        number.text = "${contact.number}"
        address.text = "${contact.address}"
        group.text = "${contact.group}"

        return view
    }

    public fun updateContactList(newList: List<Contact>){
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }
}