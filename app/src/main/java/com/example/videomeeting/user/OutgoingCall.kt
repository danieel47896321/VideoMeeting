package com.example.videomeeting.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User

class OutgoingCall : AppCompatActivity() {
    private lateinit var type: String
    private lateinit var dest: String
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
        type = (intent.getSerializableExtra("type") as? String)!!
        dest = (intent.getSerializableExtra("dest") as? String)!!
        imageViewButtonCancel = findViewById<ImageView>(R.id.imageViewButtonCancel)
        textViewType = findViewById<TextView>(R.id.textViewType)
        textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        if( type == "Call")
            textViewType.text = "${resources.getString(R.string.Call)}..."
        else
            textViewType.text = "${resources.getString(R.string.VideoCall)}..."
        textViewFullName.text = dest
        buttonCancel()
    }
    private fun buttonCancel() { imageViewButtonCancel.setOnClickListener{ onBackPressed() } }
    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}