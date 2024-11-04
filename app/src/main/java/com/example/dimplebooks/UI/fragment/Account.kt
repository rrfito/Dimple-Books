package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.util.VersionInfo
import android.os.Bundle
import android.util.Log
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
import com.example.dimplebooks.UI.activity.Auth
import com.example.dimplebooks.UI.activity.accountActivity.aboutUs
import com.example.dimplebooks.UI.activity.accountActivity.settings
import com.example.dimplebooks.UI.activity.accountActivity.versionInformation
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.UI.adapters.ListAdapter
import com.example.dimplebooks.model.ListModel
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.viewModel.historyBookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Account : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

   

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
        val maxbook = accountView.findViewById<TextView>(R.id.maxbook)

        val database = AppDatabase.getDatabase(requireContext())
        val bookHistoryDao = database.bookHistoryDao()
        viewModelHistory = ViewModelProvider(this, historyBookViewModelFactory(bookHistoryDao)).get(historyBookViewModel::class.java)


        //email and username text view
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        val usernamefire = accountView.findViewById<TextView>(R.id.usernamefire)
        val emailfire = accountView.findViewById<TextView>(R.id.emailfire)
        userId?.let {
            databaseReference.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val email = snapshot.child("email").getValue(String::class.java)
                    val username = snapshot.child("username").getValue(String::class.java)
                    emailfire.text = email ?: "Email tidak ditemukan"
                    usernamefire.text = username ?: "Username tidak ditemukan"
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Gagal mengambil data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }


        maxbook.text = "${viewModelHistory.maxbookCount} left"
        viewModelHistory.getHistorybookCount()?.observe(viewLifecycleOwner) { count ->
            val currentcount = count ?: 0
            countbook.text = currentcount.toString() ?: "0"
            progressBar.progress = currentcount
            viewModelHistory.maxbookCount -= 1
            maxbook.text = "${viewModelHistory.maxbookCount} left"


            Log.d("HistoryFragment", "Current count: ${viewModelHistory.maxbookCount} and progress bar : ${progressBar.progress}")
        }

//        (bookHistoryDao as historyBookViewModel).getHistorybookCount()
//            ?.observe(viewLifecycleOwner) { count ->
//                countbook.text = count.toString() ?: "0"
//                progressBar.progress = count ?: 0
//            }





        val listview : ListView = accountView.findViewById(R.id.ListAccountMenu)
        val menulist = listOf(
            ListModel("Settings",R.drawable.baseline_settings_24),
            ListModel("Version Information",R.drawable.baseline_security_24),
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
                showLogoutDialog()


            }else if(selectedItem.name == "Settings"){
                val intent = Intent(requireContext(), settings::class.java)
                startActivity(intent)
            }else if(selectedItem.name == "Version Information"){
                val intent = Intent(requireContext(), versionInformation::class.java)
                startActivity(intent)
            }else if(selectedItem.name == "About Us"){
                val intent = Intent(requireContext(), aboutUs::class.java)
                startActivity(intent)
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
    private fun showLogoutDialog() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // ID klien web Anda
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val FirebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.signOut()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(requireContext(), Auth::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }

            dialog.dismiss()
        }


            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }

    }


