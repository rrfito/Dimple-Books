package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.Auth
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.entity.UserEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {

    private lateinit var regisUser: EditText
    private lateinit var regisPas: EditText
    private lateinit var regisEmail: EditText
    private lateinit var buttRegis: Button
//    private lateinit var backArrow: ImageView
    private lateinit var sharedPreferences: SharedPreferences


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
                viewLifecycleOwner.lifecycleScope.launch {
                    addUser(inputUsername,inputPassword,inputEmail)
                }

                val editor = sharedPreferences.edit()
                editor.putString("isLogin", "1")
                editor.putString("username", inputUsername)
                editor.putString("password", inputPassword)


                val loginfragment = Login()
                val bundle = Bundle().apply {
                    putString("username", inputUsername)
                    putString("password", inputPassword)
                }
                loginfragment.arguments = bundle
                Log.d("RegisterFragment", "$bundle")

                (activity as? Auth)?.ReplaceFragment(loginfragment)


            } else {
                Snackbar.make(view, "Username dan Password jangan kosong", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        Toast.makeText(requireContext(), "Isi dahulu", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }

        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")
        Log.d("LoginFragmentt", "Username: $username")

    }
     suspend fun addUser(userName: String, password: String, email: String) {
         val db = AppDatabase.getDatabase(requireContext())
        val user = UserEntity(username = userName,password = password, email = email)
        val userId = db.userEntitiyDao().insertUser(user)
    }

}



