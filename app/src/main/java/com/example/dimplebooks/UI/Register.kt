package com.example.dimplebooks.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        val regisUser = findViewById<EditText>(R.id.InputUsername)
        val regisPas = findViewById<EditText>(R.id.InputPassword)
        val regisConfirmPas = findViewById<EditText>(R.id.ConfirmPassowrd)
        val shared: SharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val WrongPassowrd = findViewById<TextView>(R.id.ConfirmpassowrdValid)
        val WrongPassowrd2 = findViewById<TextView>(R.id.passowrdValid)
        val buttRegis = findViewById<Button>(R.id.Register)

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

                    val dashboard = Intent(this, Login::class.java)
                    startActivity(dashboard)
                } else {
                    WrongPassowrd.text = "Password Berbeda"
                    WrongPassowrd2.text = "Password Berbeda"
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

