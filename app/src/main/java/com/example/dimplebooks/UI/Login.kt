package com.example.dimplebooks.UI

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.MainNavigasi as MainNavigasi1

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
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        val usernamee = findViewById<EditText>(R.id.InputUsernamee)
        val passwordd = findViewById<EditText>(R.id.InputPasswordd)
        val Loginn = findViewById<Button>(R.id.Loginn)

        val buatAccount = findViewById<TextView>(R.id.buatAccount)

        val shared: SharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val isLogin = shared.getString("isLogin",null)
        
        if(isLogin=="1"){
            intent(this, MainNavigasi1::class.java)
        }

        buatAccount.setOnClickListener(){
            intent(this, Register::class.java)
        }



        Loginn.setOnClickListener(){
            val inputUsername = usernamee.text.toString()
            val inputPassword = passwordd.text.toString()
            val userVal = shared.getString("username",null)
            val passVal = shared.getString("password",null)
            if (inputPassword==passVal && inputUsername==userVal){
                val editorr = shared.edit()
                editorr.putString("isLogin", "1")
                editorr.apply()
                intent(this, MainNavigasi1::class.java)
            }else{
                Toast.makeText(this, "username dan password salah", Toast.LENGTH_LONG).show()
            }

        }



    }
    fun intent(now: Activity, after: Class<*>) {
        val intent = Intent(now, after)
        startActivity(intent)
        finish()
    }
}