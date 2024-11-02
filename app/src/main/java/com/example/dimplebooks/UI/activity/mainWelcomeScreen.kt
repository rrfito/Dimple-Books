package com.example.dimplebooks.UI.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.welcomeScreenAdapter
import com.example.dimplebooks.UI.fragment.welcomScreen.Welcome1
import com.example.dimplebooks.UI.fragment.welcomScreen.Welcome2
import com.example.dimplebooks.UI.fragment.welcomScreen.Welcome3
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class mainWelcomeScreen : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsindicator : DotsIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_welcome_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dotsindicator = findViewById(R.id.dotsWelcome)
        val skipbutton = findViewById<TextView>(R.id.skipButtonWelcome)
        val nextbutton = findViewById<Button>(R.id.nextButtonWelcome)
        viewPager = findViewById(R.id.viewpagerWelcome)
        val fragmentList = listOf(Welcome1(), Welcome2(), Welcome3())
        val adapter = welcomeScreenAdapter(this,fragmentList)
        viewPager.adapter = adapter
        dotsindicator.attachTo(viewPager)


        nextbutton.setOnClickListener {
            if (viewPager.currentItem < fragmentList.size - 1) {
                viewPager.currentItem += 1
            } else {
                FinishWelcomeScreen()
            }
        }
            skipbutton.setOnClickListener(){
            FinishWelcomeScreen()
        }
        viewPager.registerOnPageChangeCallback(object :ViewPager2
        .OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position== fragmentList.size-1){
                    nextbutton.text="Finish"

                }else{
                    nextbutton.text="Next"


                }
            }
        })


    }
    private fun FinishWelcomeScreen(){
        startActivity(Intent(this, Auth::class.java))
        finish()
    }
    }
