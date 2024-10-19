package com.example.dimplebooks.UI.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.dimplebooks.R
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Account : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountView = inflater.inflate(R.layout.fragment_account, container, false)
        val logout = accountView.findViewById<Button>(R.id.logoutButton)
        val shared: SharedPreferences = requireContext().getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val isLogin = shared.getString("isLogin",null)


        logout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah Anda yakin ingin logout?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                Toast.makeText(requireContext(), "Logout berhasil!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                val editorr = shared.edit()
                editorr.putString("isLogin", null)
                editorr.apply()
                activity?.let { finishAffinity(it) }
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }




        return accountView
    }


    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                Toast.makeText(view.context, "Undo berhasil!", Toast.LENGTH_SHORT).show()
            }.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Account().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
