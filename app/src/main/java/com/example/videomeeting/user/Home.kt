package com.example.videomeeting.user

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var backIcon: ImageView
    private lateinit var title: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }
    private fun init() {
        user = (intent.getSerializableExtra("user") as? User)!!
        backIcon = findViewById<ImageView>(R.id.backIcon)
        title = findViewById<TextView>(R.id.title)
        textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        title.setText(R.string.Home)
        backIcon.setImageDrawable(resources.getDrawable(R.drawable.signout))
        val fullName = "${user.firstName} ${user.lastName}"
        textViewFullName.text = fullName
        textViewEmail.text = user.email
        backIcon()
    }
    private fun backIcon() { backIcon.setOnClickListener{ onBackPressed() } }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.SignOut))
            .setMessage(resources.getString(R.string.AreYouSure)).setCancelable(true)
            .setPositiveButton(resources.getString(R.string.Yes)) { _, _ ->
                val firebaseAuth = FirebaseAuth.getInstance()
                if(firebaseAuth.currentUser != null)
                    firebaseAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.setNegativeButton(resources.getString(R.string.No)) { _, _ -> }.show()
    }
}