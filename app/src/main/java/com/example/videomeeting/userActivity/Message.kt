package com.example.videomeeting.userActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User

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
        imageViewBackIcon.setOnClickListener{
            onBackPressed()
        }
    }
    override fun onBackPressed() {
       finish()
    }
}