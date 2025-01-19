package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.Auth
import com.example.dimplebooks.UI.viewModel.FirebaseViewModel
import com.example.dimplebooks.data.Firebase.FirebaseAuthService
import com.example.dimplebooks.data.Firebase.FirebaseRepository

import com.example.dimplebooks.data.model.userModel
import com.example.dimplebooks.utils.Resource
import com.example.dimplebooks.utils.ViewModelFactoryFirebase
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Register : Fragment() {

    private lateinit var regisUser: EditText
    private lateinit var regisPas: EditText
    private lateinit var regisEmail: EditText
    private lateinit var buttRegis: Button
//    private lateinit var backArrow: ImageView
    private lateinit var sharedPreferences: SharedPreferences
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)


        regisUser = view.findViewById(R.id.InputUsernameRegister)
        regisPas = view.findViewById(R.id.inputpassword)
        regisEmail = view.findViewById(R.id.inputemail)
        buttRegis = view.findViewById(R.id.registerButton)

        sharedPreferences = requireActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE)



        val sigIn = view.findViewById<TextView>(R.id.sigIn)
        sigIn.setOnClickListener {
            val loginfragment = Login()
            (activity as? Auth)?.ReplaceFragment(loginfragment)
        }





        buttRegis.setOnClickListener { view ->
            val inputUsername = regisUser.text.toString()
            val inputPassword = regisPas.text.toString()
            val inputEmail = regisEmail.text.toString()
            if (inputPassword.isNotEmpty() && inputUsername.isNotEmpty()) {
                firebaseViewModel.register(inputEmail,inputPassword)
                firebaseViewModel.updateUsername(inputUsername)
            } else {
                Snackbar.make(view, "Username dan Password jangan kosong", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        Toast.makeText(requireContext(), "Isi dahulu", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }
        firebaseViewModel.registerState.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Empty -> {
                    Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    val error = resources.message
                    if (error != null) {
                        Fail(error)
                    }
                }
                is Resource.Loading ->{
                    Toast.makeText(requireContext(), "loading....", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val loginfragment = Login()
                    success("account created successfully")
                    firebaseViewModel.logout()
                    (activity as? Auth)?.ReplaceFragment(loginfragment)
                }
            }

        }

        return view
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
    private fun success(messagee : String) {
        val dialog = Dialog(requireContext())
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


}



