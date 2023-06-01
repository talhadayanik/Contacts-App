package com.example.contactsapp.ui.colleagues

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ColleaguesFragment : Fragment() {

    private var activityContext : Context? = null

    lateinit var listColleagues : ListView
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
        val view: View = (activityContext as Activity).layoutInflater.inflate(R.layout.fragment_colleagues, null, true)

        listColleagues = view.findViewById(R.id.listColleagues)
        contactList = mutableListOf()

        val contactAdapter = ContactAdapter(requireContext(),contactList)
        listColleagues.adapter = contactAdapter

        listColleagues.setOnItemClickListener { parent, view, position, id ->
            MainActivity.selectedContact = contactList[position]
            var i = Intent(activityContext, activity_details::class.java)
            startActivity(i)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val contactAdapter = ContactAdapter(requireContext(),contactList)
        listColleagues.adapter = contactAdapter

        (context as LifecycleOwner).lifecycleScope.launch {
            contactList = getData() as MutableList<Contact>
            contactAdapter.updateContactList(contactList)
        }
    }

    suspend fun getData() = withContext(Dispatchers.IO){
        MainActivity.db.contactDao().getDataByGroup("Colleagues")
    }
}