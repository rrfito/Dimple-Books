package com.example.refitoo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usernamee = findViewById<EditText>(R.id.InputUsernamee)
        val passwordd = findViewById<EditText>(R.id.InputPasswordd)
        val Loginn = findViewById<Button>(R.id.Loginn)

        val buatAccount = findViewById<TextView>(R.id.buatAccount)
        val intent = Intent(this,Register::class.java)

        buatAccount.setOnClickListener(){
            startActivity(intent)
            finish()
        }



        Loginn.setOnClickListener(){
            val inputUsername = usernamee.text.toString()
            val inputPassword = passwordd.text.toString()
            val shared = getSharedPreferences("userpref", MODE_PRIVATE)
            val userVal = shared.getString("username",null)
            val passVal = shared.getString("password",null)
            if (inputPassword==passVal && inputUsername==userVal){
                val dashboard = Intent(this,Dashboard::class.java)
                dashboard.putExtra("EXTRA_MESSAGE", "Hello fito")
                startActivity(dashboard)
                finish()
            }else{
                Toast.makeText(this, "username dan password salah", Toast.LENGTH_LONG).show()
            }

        }



    }
}