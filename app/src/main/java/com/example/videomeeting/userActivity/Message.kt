package com.example.videomeeting.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.videomeeting.R
import com.example.videomeeting.controller.HomeController
import com.example.videomeeting.guestActivity.VideoMeeting
import com.example.videomeeting.model.HomeModel
import com.example.videomeeting.myClass.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class Message : AppCompatActivity() {
    private lateinit var userName: TextView
    private lateinit var imageViewBackIcon: ImageView
    private lateinit var imageViewMoreVert: ImageView
    private lateinit var imageViewUser: ImageView
    private lateinit var cardViewUserImage: CardView
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        init()
    }
    private fun init() {
        user = (intent.getSerializableExtra("user") as? User)!!
        userName = findViewById<TextView>(R.id.userName)
        imageViewBackIcon = findViewById<ImageView>(R.id.imageViewBackIcon)
        imageViewMoreVert = findViewById<ImageView>(R.id.imageViewMoreVert)
        cardViewUserImage = findViewById<CardView>(R.id.cardViewUserImage)
        imageViewUser = findViewById<ImageView>(R.id.imageViewUser)
        setBackIcon()
    }

    private fun setBackIcon() {
        onBackPressed()
    }
    override fun onBackPressed() {
       finish()
    }
}