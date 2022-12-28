package com.example.videomeeting.user

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.google.firebase.installations.InstallationTokenResult.builder

class OutgoingCall : AppCompatActivity() {
    private lateinit var type: String
    private lateinit var destUser: User
    private lateinit var user: User
    private lateinit var imageViewButtonCancel: ImageView
    private lateinit var textViewType: TextView
    private lateinit var textViewFullName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoing_call)
        init()
    }
    private fun init() {
        user = (intent.getSerializableExtra("user") as? User)!!
        destUser = (intent.getSerializableExtra("destUser") as? User)!!
        type = (intent.getSerializableExtra("type") as? String)!!
        imageViewButtonCancel = findViewById<ImageView>(R.id.imageViewButtonCancel)
        textViewType = findViewById<TextView>(R.id.textViewType)
        textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        if( type == "Call")
            textViewType.text = "${resources.getString(R.string.Call)}..."
        else
            textViewType.text = "${resources.getString(R.string.VideoCall)}..."
        textViewFullName.text = "${destUser.firstName} ${destUser.lastName}"
        setNewCall()
        buttonCancel()
    }
    private fun setNewCall(){
        if( type == "Call"){

        }
    }
    private fun buttonCancel() { imageViewButtonCancel.setOnClickListener{ onBackPressed() } }
    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}