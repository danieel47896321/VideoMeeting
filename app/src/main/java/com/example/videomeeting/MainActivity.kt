package com.example.videomeeting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.videomeeting.guest.VideoMeeting
import com.example.videomeeting.myClass.User
import com.example.videomeeting.user.Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null && firebaseAuth.currentUser!!.isEmailVerified) {
            getUser()
        } else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, VideoMeeting::class.java))
                finish()
            }, 300)
        }
    }
    private fun getUser() {
        if (firebaseAuth.currentUser != null) {
            databaseReference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    moveToHome(user)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
    private fun moveToHome(user: User?) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}