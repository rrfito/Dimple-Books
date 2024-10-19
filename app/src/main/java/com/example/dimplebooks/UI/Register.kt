package com.example.dimplebooks.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dimplebooks.R
import com.google.android.material.snackbar.Snackbar

class Register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        val regisUser = findViewById<EditText>(R.id.InputUsernameRegister)
        val regisPas = findViewById<EditText>(R.id.InputPasswordRegister)
        val regisConfirmPas = findViewById<EditText>(R.id.inputConfirmPasswordRegister)
        val shared: SharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val buttRegis = findViewById<Button>(R.id.registerButton)

        val backArrow = findViewById<ImageView>(R.id.backArrow)

        backArrow.setOnClickListener(){
            val loginBack = Intent(this, Login::class.java)
            startActivity(loginBack)
            finish()
        }



        buttRegis.setOnClickListener() {
            val inputUsername = regisUser.text.toString()
            val inputPassword = regisPas.text.toString()
            val inputConfirmPassword = regisConfirmPas.text.toString()
            if (inputPassword.isNotEmpty() && inputUsername.isNotEmpty()) {
                if (inputPassword == inputConfirmPassword) {
                    val editor = shared.edit()
                    editor.putString("username", inputUsername)
                    editor.putString("password", inputPassword)
                    editor.apply()

                    val LoginBack = Intent(this, Login::class.java)
                    startActivity(LoginBack)
                } else {

                    Toast.makeText(
                        this,
                        "Password dan Konfirmasi Password tidak sama",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                    Snackbar.make(it, "Usarname dan Passowrd jangan kosong", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            Toast.makeText(this, "Isi dahulu", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                }

            }
        }
    }

