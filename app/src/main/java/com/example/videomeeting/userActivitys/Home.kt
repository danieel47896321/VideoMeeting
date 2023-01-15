package com.example.videomeeting.userActivitys

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.R
import com.example.videomeeting.adapter.HomeAdapter
import com.example.videomeeting.controller.HomeController
import com.example.videomeeting.guestActivity.VideoMeeting
import com.example.videomeeting.model.HomeModel
import com.example.videomeeting.myClass.User

class Home : AppCompatActivity() {
    private lateinit var homeModel: HomeModel
    private lateinit var homeController: HomeController
    private lateinit var backIcon: ImageView
    private lateinit var title: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }
    private fun init() {
        homeModel = ViewModelProvider(this)[HomeModel::class.java]
        homeController = HomeController(homeModel, this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        backIcon = findViewById<ImageView>(R.id.backIcon)
        title = findViewById<TextView>(R.id.title)
        textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        backIcon.setImageResource(R.drawable.signout)
        homeController.setView()
        backIcon()
        setStatus()
    }
    fun updateView(title: Int, fullName: String?) {
        this.title.setText(title)
        textViewFullName.text = fullName
    }
    fun showUsers(users: ArrayList<User>) {
        recyclerView.adapter = HomeAdapter(users)
    }
    private fun backIcon() { backIcon.setOnClickListener{ onBackPressed() } }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.SignOut))
            .setMessage(resources.getString(R.string.AreYouSure)).setCancelable(true).setPositiveButton(resources.getString(R.string.Yes)) { _, _ ->
                homeController.signOut()
                startActivity(Intent(this, VideoMeeting::class.java))
                finish()
            }.setCancelable(false).setNegativeButton(resources.getString(R.string.No)) { _, _ -> }.show()
    }
    private fun setStatus(){
        if(homeModel.getAuth().uid != null)
            homeModel.getData().child(homeModel.getAuth().uid!!).child("status").setValue("Online")
        homeModel.getData().child(homeModel.getAuth().uid!!).child("status").onDisconnect().setValue("Offline")
    }
}