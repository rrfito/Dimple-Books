package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.ListAdapter
import com.example.dimplebooks.model.ListModel
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.viewModel.historyBookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModelFactory
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Account : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModelHistory: historyBookViewModel

   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountView = inflater.inflate(R.layout.fragment_account, container, false)

        val progressBar = accountView.findViewById<ProgressBar>(R.id.customProgressBar)
        val countbook = accountView.findViewById<TextView>(R.id.countbook)

        val database = AppDatabase.getDatabase(requireContext())
        val bookHistoryDao = database.bookHistoryDao()
        viewModelHistory = ViewModelProvider(this, historyBookViewModelFactory(bookHistoryDao)).get(historyBookViewModel::class.java)

        viewModelHistory.getHistorybookCount()?.observe(viewLifecycleOwner) { count ->
            countbook.text = count.toString() ?: "0"
            progressBar.progress = count ?: 0
        }

//        (bookHistoryDao as historyBookViewModel).getHistorybookCount()
//            ?.observe(viewLifecycleOwner) { count ->
//                countbook.text = count.toString() ?: "0"
//                progressBar.progress = count ?: 0
//            }





        val listview : ListView = accountView.findViewById(R.id.ListAccountMenu)
        val menulist = listOf(
            ListModel("Username",R.drawable.account),
            ListModel("Email",R.drawable.baseline_alternate_email_24),
            ListModel("Password",R.drawable.baseline_security_24),
            ListModel("About Us",R.drawable.baseline_handshake_24),
            ListModel("Log Out",R.drawable.baseline_logout_24),

            )


        val adapter = ListAdapter(requireContext(),menulist)
        listview.adapter = adapter

        listview.setOnItemClickListener{ parent,view,position,id ->
            val selectedItem = menulist[position]
            if(selectedItem.name == "Log Out"){
                val shared = requireContext().getSharedPreferences("activeUserId", Context.MODE_PRIVATE)
                val exit =shared.edit().remove("activeUserId").apply()
                requireActivity().finish()

            }

        }



        return accountView
    }


    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                Toast.makeText(view.context, "Undo berhasil!", Toast.LENGTH_SHORT).show()
            }.show()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Account().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
