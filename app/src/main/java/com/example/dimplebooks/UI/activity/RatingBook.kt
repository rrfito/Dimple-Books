package com.example.dimplebooks.UI.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dimplebooks.UI.utils.ViewModelFactory
import com.example.dimplebooks.UI.viewModel.ReviewViewModel
import com.example.dimplebooks.data.model.Review
import com.example.dimplebooks.data.network.RetrofitInstance
import com.example.dimplebooks.data.repository.ApiRepository
import com.example.dimplebooks.data.repository.reviewRepository
import com.example.dimplebooks.databinding.ActivityRatingBookBinding
import com.example.dimplebooks.utils.Resource
import com.example.dimplebooks.utils.ViewModelFactoryReview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RatingBook : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBookBinding
    private lateinit var reviewViewModeli: ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRatingBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val factory = ViewModelFactoryReview(reviewRepository(RetrofitInstance.ReviewApi))
        reviewViewModeli = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)


        createProduct()
    }

    private fun createProduct() {
        var rating: Float = 0f
        binding.ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            rating = newRating
        }

        binding.sendrating.setOnClickListener {
            val comment = binding.comment.text.toString().trim()

            val sharedPreferences = this.getSharedPreferences("book", Context.MODE_PRIVATE)
            val bookTitle = sharedPreferences.getString("book_title", "")
            val user = FirebaseAuth.getInstance().currentUser
            val userId = user?.uid
            val username = FirebaseAuth.getInstance().currentUser?.displayName ?: "Anonim"
            val imageUrl = user?.photoUrl
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            Log.d("RatingBook", "Book Title: $bookTitle, User ID: $userId, Username: $username, Rating: $rating, Comment: $comment")

            if (!bookTitle.isNullOrEmpty() && userId != null) {
                val review = listOf(
                    Review(
                        userid = userId,
                        username = username,
                        bookTitle = bookTitle,
                        rating = rating,
                        comment = comment,
                        imageUrl = imageUrl.toString(),
                        dateTime = LocalDateTime.now().format(formatter)
                    )
                )
                Log.d("Review Model","review ${review.toString()}")


                reviewViewModeli.createReviews(this, review)


                reviewViewModeli.createReview.observe(this) { resource ->
                    Log.d("dqqdq","qdqdqwdqd")
                    when (resource) {
                        is Resource.Success -> {
                            Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is Resource.Error -> {

                            Toast.makeText(this, resource.message ?: "Failed to submit review.", Toast.LENGTH_LONG).show()
                            Log.d("ErrorError","${resource.message}")
                        }
                        is Resource.Empty -> {
                            Toast.makeText(this, "Review Empty!", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Toast.makeText(this, "Review loading !", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Invalid input. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
