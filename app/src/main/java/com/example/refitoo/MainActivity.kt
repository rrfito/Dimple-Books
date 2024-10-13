package com.example.refitoo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val button : Button = findViewById(R.id.button)
        val edittext : EditText = findViewById(R.id.editTextNumber)
        val textview : TextView = findViewById(R.id.textView)

        button.setOnClickListener(){

            val edit = edittext.text.toString()
            if(edit.isNotEmpty()){
                val a = edit.toInt() * 3
                textview.text = a.toString()
            }else{
                Toast.makeText(this, "isi terlebih dahulu", Toast.LENGTH_LONG).show()
            }

        }
        val next : Button = findViewById<Button>(R.id.button3)
        next.setOnClickListener(){
            val pindah = Intent(this,Activity2::class.java)
            pindah.putExtra("name","HEALTCARE")
            startActivity(pindah)
        }


    }







}