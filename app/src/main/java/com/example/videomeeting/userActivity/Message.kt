package com.example.videomeeting.userActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.adapter.MessageAdapter
import com.example.videomeeting.controller.MessageController
import com.example.videomeeting.model.MessageModel
import com.example.videomeeting.myClass.Chat
import com.example.videomeeting.myClass.User
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.*

class Message : AppCompatActivity() {
    private lateinit var messageModel: MessageModel
    private lateinit var messageController: MessageController
    private lateinit var userName: TextView
    private lateinit var textViewSend: TextView
    private lateinit var buttonSend: ImageButton
    private lateinit var imageViewBackIcon: ImageView
    private lateinit var imageViewCall: ImageView
    private lateinit var imageViewVideo: ImageView
    private lateinit var imageViewUser: ImageView
    private lateinit var recyclerView: RecyclerView
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
        textViewSend = findViewById<TextView>(R.id.textViewSend)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        buttonSend = findViewById<ImageButton>(R.id.buttonSend)
        imageViewBackIcon = findViewById<ImageView>(R.id.imageViewBackIcon)
        imageViewCall = findViewById<ImageView>(R.id.imageViewCall)
        imageViewVideo = findViewById<ImageView>(R.id.imageViewVideo)
        cardViewUserImage = findViewById<CardView>(R.id.cardViewUserImage)
        imageViewUser = findViewById<ImageView>(R.id.imageViewUser)
        messageController.setInfo(user)
        messageController.getMessages()
        setBackIcon()
        setSendMessage()
        setCallIcon()
        setVideoCallIcon()
    }
    private fun setCallIcon() {
        imageViewCall.setOnClickListener {
            outGoingCall("Call")
        }
    }
    private fun setVideoCallIcon() {
        imageViewVideo.setOnClickListener {
            outGoingCall("Video")
        }
    }
    private fun outGoingCall(type: String) {
        val intent = Intent(this, OutgoingCall::class.java)
        intent.putExtra("destUser", user)
        intent.putExtra("type", type)
        startActivity(intent)
    }
    private fun setSendMessage() {
        buttonSend.setOnClickListener {
            if (textViewSend.text.toString() != "") {
                val currentDateTime = SimpleDateFormat("HH:mm dd-MM-yyyy").format(Date())
                val chat = Chat(messageController.getSender(), messageController.getReceiver(), textViewSend.text.toString(), currentDateTime)
                messageController.sendMessage(chat)
                textViewSend.text = ""
            }
        }
    }
    private fun setBackIcon() {
        imageViewBackIcon.setOnClickListener {
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
    fun setMessages(chats: ArrayList<Chat>) {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MessageAdapter(chats, user, this)
    }
}