package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.Auth
import com.example.dimplebooks.UI.activity.HistoryBooks
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.entity.bookHistoryEntity
import com.example.dimplebooks.UI.viewModel.historyBookViewModel
import com.example.dimplebooks.databinding.FragmentHistoryBinding
import com.example.dimplebooks.utils.historyBookViewModelFactory
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

class Account : Fragment(),bookHistoryAdapter.OnItemClickListener {

    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var historyBookList: ArrayList<bookHistoryEntity>
    private lateinit var historyAdapter: bookHistoryAdapter


//    private val uservidemodel : userviewmodel by lazy {
//        va; repository = userRepository(Retrofitinstance.getJsonPlaceholder())
//        viewmodelprovider(
//            this,viewmodelFactory(userViewmodel::class.java){(uservidemodel(repository))}
//        )(userviewmodel::class.java)
//    }


   



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val accountView = inflater.inflate(R.layout.fragment_account, container, false)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.navy_blue_white)
        val progressBar = accountView.findViewById<ProgressBar>(R.id.customProgressBar)
        val countbook = accountView.findViewById<TextView>(R.id.countbook)
        val maxbook = accountView.findViewById<TextView>(R.id.maxbook)

        val database = AppDatabase.getDatabase(requireContext())
        val bookHistoryDao = database.bookHistoryDao()
        viewModelHistory = ViewModelProvider(this, historyBookViewModelFactory(bookHistoryDao)).get(
            historyBookViewModel::class.java)


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
        if (userId != null) {
            viewModelHistory.getHistorybookCount(userId)?.observe(viewLifecycleOwner) { count ->
                val currentcount = count ?: 0
                countbook.text = currentcount.toString() ?: "0"
                progressBar.progress = currentcount
                viewModelHistory.maxbookCount -= 1
                maxbook.text = "${viewModelHistory.maxbookCount} left"


                Log.d("HistoryFragment", "Current count: ${viewModelHistory.maxbookCount} and progress bar : ${progressBar.progress}")
            }
        }

//        (bookHistoryDao as historyBookViewModel).getHistorybookCount()
//            ?.observe(viewLifecycleOwner) { count ->
//                countbook.text = count.toString() ?: "0"
//                progressBar.progress = count ?: 0
//            }


        val historybutton : LinearLayout = accountView.findViewById(R.id.History)
        historybutton.setOnClickListener(){
            val intent = Intent(requireContext(), HistoryBooks::class.java)
            startActivity(intent)

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
        val sharedPreferences = requireActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
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
    override fun onItemClick(book: bookHistoryEntity) {
        val intent = Intent(requireContext(), detailBook::class.java)
        intent.putExtra("book_title", book.title)
        intent.putExtra("book_image", book.imageUrl)
        intent.putExtra("book_authors", book.authors)
        intent.putExtra("book_publisher", book.publisher)
        intent.putExtra("book_publishedDate", book.publishedDate)
        intent.putExtra("book_pageCount", book.pageCount)
        intent.putExtra("book_language", book.language)
        intent.putExtra("book_categories", book.categories)
        intent.putExtra("book_description", book.description)
        intent.putExtra("book_price", book.price)
        intent.putExtra("book_saleability", book.saleability)
        intent.putExtra("buyLink", book.buyLink)



        startActivity(intent)
    }

//    fun setupnewhorizontalApi(binding : FragmentHistoryBinding) {
//        val adapter = newhorizontaladapter(emptylist())
//        userviewmodel.getUsers().observe(requireActivity()) { resp ->
//            if (resp != null) {
//                val newsitem = resp.mapIndexed(index, data)->
//                newhorizontalmodel(data.name, "hteqqwq")
//
//            } adapter . updatedata (newsitem)
//        }
//    }binding.newhorlist.adapter = adapter
//    binding.newhorlist.layoutmanager = "intinya masukin layout"
//    }

    }


