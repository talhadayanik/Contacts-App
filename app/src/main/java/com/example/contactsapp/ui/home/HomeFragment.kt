package com.example.contactsapp.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.contactsapp.MainActivity
import com.example.contactsapp.R
import com.example.contactsapp.activity_details
import com.example.contactsapp.adapters.ContactAdapter
import com.example.contactsapp.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var activityContext : Context? = null

    lateinit var btnSearch : ImageButton
    lateinit var txtSearch : EditText
    lateinit var listHome: ListView
    lateinit var contactList : MutableList<Contact>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onDetach() {
        super.onDetach()
        activityContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = (activityContext as Activity).layoutInflater.inflate(R.layout.fragment_home, null, true)

        contactList = mutableListOf()
        listHome = view.findViewById(R.id.listHome)
        txtSearch = view.findViewById(R.id.txtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)

        val contactAdapter = ContactAdapter(requireContext(),contactList)
        listHome.adapter = contactAdapter

        listHome.setOnItemClickListener { parent, view, position, id ->
            MainActivity.selectedContact = contactList[position]
            var i = Intent(activityContext, activity_details::class.java)
            startActivity(i)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val contactAdapter = ContactAdapter(requireContext(),contactList)
        listHome.adapter = contactAdapter

        txtSearch.setText("")

        (activityContext as LifecycleOwner).lifecycleScope.launch {
            contactList = getData() as MutableList<Contact>
            contactAdapter.updateContactList(contactList)
        }

        btnSearch.setOnClickListener {
            (activityContext as LifecycleOwner).lifecycleScope.launch {
                contactList = search(txtSearch.text.toString()) as MutableList<Contact>
                contactAdapter.updateContactList(contactList)
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }

        }
    }

    suspend fun getData() = withContext(Dispatchers.IO){
        MainActivity.db.contactDao().getLastTenContact()
    }

    suspend fun search(searchTerm : String) = withContext(Dispatchers.IO){
        MainActivity.db.contactDao().searchByName(searchTerm)
    }
}