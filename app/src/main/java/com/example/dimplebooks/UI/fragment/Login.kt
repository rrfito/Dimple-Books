package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.Auth
import com.example.dimplebooks.UI.activity.MainNavigasi
import com.example.dimplebooks.UI.utils.ViewModelFactory
import com.example.dimplebooks.UI.viewModel.FirebaseViewModel
import com.example.dimplebooks.data.Firebase.FirebaseAuthService
import com.example.dimplebooks.data.Firebase.FirebaseRepository
import com.example.dimplebooks.data.database.AppDatabase
import com.example.dimplebooks.utils.Resource
import com.example.dimplebooks.utils.ViewModelFactoryFirebase

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Login : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var createAccountTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: AppDatabase
    private lateinit var googleSignInClient: GoogleSignInClient
    private val firebaseViewModel: FirebaseViewModel by viewModels {
        ViewModelFactoryFirebase(FirebaseViewModel::class.java) {
            val repository = FirebaseRepository(FirebaseAuthService())
            FirebaseViewModel(repository)
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inisialisasi view
        emailEditText = view.findViewById(R.id.inputemail)
        passwordEditText = view.findViewById(R.id.InputPasswordd)
        loginButton = view.findViewById(R.id.Loginn)
        createAccountTextView = view.findViewById(R.id.buatAccount)



        if(firebaseViewModel.getCurrentUser() != null){
            navigateTo(MainNavigasi::class.java)
        }
        loginButton.setOnClickListener {
            val inputEmail = emailEditText.text.toString()
            val inputPassword = passwordEditText.text.toString()

            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                firebaseViewModel.login(inputEmail, inputPassword)
            } else {
                Toast.makeText(requireContext(), "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        firebaseViewModel.loginState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Mohon tunggu sebentar", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase User Authentication", "Mengirim Username Password...")
                }
                is Resource.Success -> {
                    val user = resource.data
                    Toast.makeText(requireContext(), "Halo ${user?.username}", Toast.LENGTH_SHORT).show()
                    navigateTo(MainNavigasi::class.java)
                }
                is Resource.Error -> {
                    val error = resource.message
                    if (error != null) {
                        Fail(error)
                    }
                }
                else -> {}
            }
        }
        //  Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val googleButton = view.findViewById<Button>(R.id.google)
        googleButton.setOnClickListener {
            signInWithGoogle()
        }

        createAccountTextView.setOnClickListener {
            (activity as? Auth)?.ReplaceFragment(Register())
        }

        return view
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(requireActivity(), activityClass)
        startActivity(intent)
        requireActivity().finish()


    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Login gagal: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    Toast.makeText(requireContext(), "Login berhasil: ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    navigateTo(MainNavigasi::class.java)
                } else {
                    Toast.makeText(requireContext(), "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun Fail(messagee : String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val message = dialog.findViewById<TextView>(R.id.textDialog)
        message.setText(messagee)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.findViewById<MaterialButton>(R.id.btnNotForSaleYes).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}