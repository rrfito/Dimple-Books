package com.example.dimplebooks.UI.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.dimplebooks.UI.activity.MainNavigasi
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.entity.UserEntity
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var createAccountTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: AppDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inisialisasi view
        usernameEditText = view.findViewById(R.id.InputUsernamee)
        passwordEditText = view.findViewById(R.id.InputPasswordd)
        loginButton = view.findViewById(R.id.Loginn)
        createAccountTextView = view.findViewById(R.id.buatAccount)
        sharedPreferences = requireActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE)



//        // Cek apakah user sudah login
//        val isLogin = sharedPreferences.getString("isLogin", null)
//        if (isLogin == "1") {
//
//            navigateTo(MainNavigasi::class.java)
//
//        }

        val bundleUsername = arguments?.getString("username")
        val bundlePassword = arguments?.getString("password")
        usernameEditText.setText(bundleUsername)
        passwordEditText.setText(bundlePassword)


        createAccountTextView.setOnClickListener {

            (activity as? Auth)?.ReplaceFragment(Register())
        }


        loginButton.setOnClickListener {
            val inputUsername = usernameEditText.text.toString()
            val inputPassword = passwordEditText.text.toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    db = AppDatabase.getDatabase(requireContext())
                    val user = db.userEntitiyDao().getUserByUsername(inputUsername)
                    if(user!=null && user.password == inputPassword){
                        saveUser(inputUsername)
//                        val editor = sharedPreferences.edit()
//
//                        editor.putString("isLogin", "1")
//                        editor.apply()
                        navigateTo(MainNavigasi::class.java)
                    }else{

                        Toast.makeText(requireContext(), "Username dan Password salah", Toast.LENGTH_LONG).show()
                    }



                }

            }



        return view
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(requireActivity(), activityClass)
        startActivity(intent)
        requireActivity().finish()
    }
    suspend fun saveUser(username : String): UserEntity? {
        db = AppDatabase.getDatabase(requireContext())
        val user = db.userEntitiyDao().getUserByUsername(username)
        user.let{
            if (it != null) {
                sharedPreferences.edit().putInt("activeUserId", it.userid).apply()

            }
        }
        return user
    }
}