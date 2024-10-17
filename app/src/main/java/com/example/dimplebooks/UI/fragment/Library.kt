package com.example.dimplebooks.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.model.RecycleViewBook
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Library.newInstance] factory method to
 * create an instance of this fragment.
 */
class Library : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_history)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val booklist = ArrayList<RecycleViewBook>()
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix", "Suzan Hasanna", 5.5, R.drawable.book2))

        val adapter = bookAdapter(booklist)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Library.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Library().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}