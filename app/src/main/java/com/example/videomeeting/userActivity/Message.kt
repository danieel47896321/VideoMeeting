package com.example.videomeeting.userActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.controller.HomeController
import com.example.videomeeting.controller.MessageController
import com.example.videomeeting.model.HomeModel
import com.example.videomeeting.model.MessageModel
import com.example.videomeeting.myClass.User

class Message : AppCompatActivity() {
    private lateinit var messageModel: MessageModel
    private lateinit var messageController: MessageController
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
        messageModel = ViewModelProvider(this)[MessageModel::class.java]
        messageController = MessageController(messageModel, this)
        user = (intent.getSerializableExtra("user") as? User)!!
        userName = findViewById<TextView>(R.id.userName)
        imageViewBackIcon = findViewById<ImageView>(R.id.imageViewBackIcon)
        imageViewMoreVert = findViewById<ImageView>(R.id.imageViewMoreVert)
        cardViewUserImage = findViewById<CardView>(R.id.cardViewUserImage)
        imageViewUser = findViewById<ImageView>(R.id.imageViewUser)
        messageController.setInfo(user)
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
    fun setUserInfo(userFullName: String, image: String) {
        userName.text = userFullName
        if (image != "none") {
            Glide.with(this).load(image).into(imageViewUser)
        }
    }
}