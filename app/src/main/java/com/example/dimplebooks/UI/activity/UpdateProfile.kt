package com.example.dimplebooks.UI.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels

import com.example.dimplebooks.R
import com.example.dimplebooks.UI.viewModel.FirebaseViewModel
import com.example.dimplebooks.data.Firebase.FirebaseAuthService
import com.example.dimplebooks.data.Firebase.FirebaseRepository
import com.example.dimplebooks.utils.Resource
import com.example.dimplebooks.utils.ViewModelFactoryFirebase
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class UpdateProfile : AppCompatActivity() {
    private val firebaseViewModel: FirebaseViewModel by viewModels {
        ViewModelFactoryFirebase(FirebaseViewModel::class.java) {
            val repository = FirebaseRepository(FirebaseAuthService())
            FirebaseViewModel(repository)
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val username: EditText = findViewById(R.id.UpdateUsername)
        val email: EditText = findViewById(R.id.UpdateEmail)
        val updateButton: MaterialButton = findViewById(R.id.updatebutton)

        val toolbar: MaterialToolbar = findViewById(R.id.updatebut)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val checkpasswrod : EditText = findViewById(R.id.Inputcheckpassword)
        val checkbutton : MaterialButton = findViewById(R.id.checkbutton)
        val cardcheckpassword : androidx.cardview.widget.CardView = findViewById(R.id.checkpassword)
        val cardUpdateEmailAndPassword : androidx.cardview.widget.CardView = findViewById(R.id.updateEmailandPassword)
        cardUpdateEmailAndPassword.visibility = View.GONE
        checkbutton.setOnClickListener {
            val inputcheckpassword = checkpasswrod.text.toString()
            if (inputcheckpassword.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
                }
            firebaseViewModel.checkpassword(inputcheckpassword)
        }
        firebaseViewModel.checkpassword.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Please wait a moment", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    cardcheckpassword.visibility = View.GONE
                    cardUpdateEmailAndPassword.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    val error = resource.message
                    if (error != null) {
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Empty -> {}
            }
        }
        updateButton.setOnClickListener {
                val inputUsername = username.text.toString().trim()
                val inputEmail = email.text.toString().trim()

                if (inputUsername.isEmpty() || inputEmail.isEmpty()) {
                    Toast.makeText(this, "Username and Email cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                    Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                updateButton.isEnabled = false
                firebaseViewModel.updateUsernameAndEmail(inputUsername, inputEmail)


        }


        firebaseViewModel.updateState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Please wait a moment", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {

                    firebaseViewModel.logout(this)
                    val intent = Intent(this, Auth::class.java)
                    startActivity(intent)
                    finish()

                    onBackPressed()
                    Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    val error = resource.message
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                }

                is Resource.Empty -> {}
            }
        }
    }
        private fun success(messagee : String) {
        val dialog = Dialog(this )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val message = dialog.findViewById<TextView>(R.id.textDialog)
        message.setText(messagee)

        val image = dialog.findViewById<ImageView>(R.id.imagesucces)
        image.setImageResource(R.drawable.succes_create_account)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.findViewById<MaterialButton>(R.id.btnNotForSaleYes).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun error(messagee : String) {
        val dialog = Dialog(this )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val message = dialog.findViewById<TextView>(R.id.textDialog)
        message.setText(messagee)

        val image = dialog.findViewById<ImageView>(R.id.imagesucces)
        image.setImageResource(R.drawable.dialog_notforsale)

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
