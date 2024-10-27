package com.example.dimplebooks.UI.fragment

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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.Auth
import com.google.android.material.snackbar.Snackbar

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
    private lateinit var regisConfirmPas: EditText
    private lateinit var buttRegis: Button
//    private lateinit var backArrow: ImageView
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        regisUser = view.findViewById(R.id.InputUsernameRegister)
        regisPas = view.findViewById(R.id.InputPasswordRegister)
        regisConfirmPas = view.findViewById(R.id.inputConfirmPasswordRegister)
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
            val inputConfirmPassword = regisConfirmPas.text.toString()
            if (inputPassword.isNotEmpty() && inputUsername.isNotEmpty()) {
                if (inputPassword == inputConfirmPassword) {
                    val editor = sharedPreferences.edit()
                    editor.putString("username", inputUsername)
                    editor.putString("password", inputPassword)
                    editor.apply()

                    val loginfragment = Login()
                    val bundle = Bundle().apply {
                        putString("username", inputUsername)
                        putString("password", inputPassword)
                    }
                    loginfragment.arguments = bundle
                    Log.d("RegisterFragment", "$bundle")

                    (activity as? Auth)?.ReplaceFragment(loginfragment)


                } else {
                    Toast.makeText(
                        requireContext(),
                        "Password dan Konfirmasi Password tidak sama",
                        Toast.LENGTH_LONG
                    ).show()
                }
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
}



