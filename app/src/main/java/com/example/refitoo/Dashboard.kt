package com.example.refitoo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class Dashboard : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logout = findViewById<Button>(R.id.Logout)
        val snackbar = findViewById<Button>(R.id.snackbar)
        val message = intent.getStringExtra("EXTRA_MESSAGE")
        val textViewMessage = findViewById<TextView>(R.id.hai)
        textViewMessage.text = message


        logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah Anda yakin ingin logout?")


            builder.setPositiveButton("Yes") { dialog, _ ->

                Toast.makeText(this, "Logout berhasil!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finishAffinity()
            }
            builder.setNegativeButton("No") { dialog, _ ->

                dialog.dismiss()
            }
            builder.show()

        }
        snackbar.setOnClickListener {
            showSnackbar("Snackbar ditampilkan!")
        }


    }fun showSnackbar(message: String) {
        val view = findViewById<android.view.View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                Toast.makeText(view.context, "Undo berhasil!", Toast.LENGTH_SHORT).show()
            }.show()
    }
}