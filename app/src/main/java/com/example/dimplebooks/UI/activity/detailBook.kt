package com.example.dimplebooks.UI.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.reviewAdapter
import com.example.dimplebooks.UI.adapters.topAppbarAdapter
import com.example.dimplebooks.model.reviewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class detailBook : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var commentAdapter: reviewAdapter
//    private val commentList = listOf(
//        reviewModel(R.drawable.profile, "John Doe", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "22 hours ago"),
//        reviewModel(R.drawable.profile, "Jane Smith", "Lorem Ipsum has been the industry's standard dummy text.", "3 days ago"),
//        reviewModel(R.drawable.profile, "Alice Brown", "It is a long established fact that a reader will be distracted.", "1 week ago"),
//        reviewModel(R.drawable.profile, "Kanap Brown", "It is a long established fact that a reader will be distracted.", "2 week ago")
//
//    )
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_detail_book)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
    window.statusBarColor = ContextCompat.getColor(this, R.color.white)


    val shared = getSharedPreferences("book", MODE_PRIVATE)
    val price = shared.getString("book_price", "")
    val buyLink = shared.getString("buyLink", "")
    val preview = shared.getString("book_previewLink", "")
    val imageUrl = shared.getString("book_image", "")

    Log.d("sharedwdqdqwqq","$price buy link : $buyLink $preview $imageUrl")
    val backgroundView = findViewById<ImageView>(R.id.backgrounddetail)
    Glide.with(this).load(imageUrl).into(backgroundView)
    // Menangani klik pada tombol kembali
    val toolbar = findViewById<MaterialToolbar>(R.id.topAppbarDetail)
    toolbar.setNavigationOnClickListener {
        this.onBackPressed()
    }


    toolbar.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.buy -> {
                if (buyLink != null) {
                    showForSaleDialog(price!!, buyLink!!)
                } else {
                    showNotForSaleDialog()
                }
                true
            }
            R.id.preview -> {
                val intent = Intent(this, Preview::class.java)
                intent.putExtra("book_previewLink", preview)
                startActivity(intent)
                true
            }
            else -> false
        }

    }
    val viewPager = findViewById<ViewPager2>(R.id.descriptionPager)
    val tabLayout = findViewById<TabLayout>(R.id.tabs)
    viewPager.adapter = topAppbarAdapter(this)

    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        tab.text = when (position) {
            0 -> "Description"
            1 -> "Reviews"
            else -> null
        }
    }.attach()
}
    private fun showForSaleDialog(price: String, buyLink: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_forsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val priceTextView = dialog.findViewById<TextView>(R.id.priceForsale)
        priceTextView.text = "Price : Rp $price"

        dialog.findViewById<MaterialButton>(R.id.btnYesForSale).setOnClickListener {
            val intent = Intent(this, buyItem::class.java)
            val shared = getSharedPreferences("book", MODE_PRIVATE)
            val buyLink = shared.getString("buyLink", "")
            intent.putExtra("buylinkk", buyLink)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.findViewById<MaterialButton>(R.id.btnNoForSale).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showNotForSaleDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

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