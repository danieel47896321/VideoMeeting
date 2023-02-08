package com.example.videomeeting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.videomeeting.controller.MainController
import com.example.videomeeting.guestActivity.VideoMeeting
import com.example.videomeeting.model.MainModel
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.Home

class MainActivity : AppCompatActivity() {
    private lateinit var mainController: MainController
    private lateinit var mainModel: MainModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init() {
        mainModel = ViewModelProvider(this)[MainModel::class.java]
        mainController = MainController(mainModel, this)
        mainController.checkCurrentUser()
    }
    fun videoMeetingPage() {
        startActivity(Intent(this, VideoMeeting::class.java))
        finish()
    }
    fun homePage(user: User) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}