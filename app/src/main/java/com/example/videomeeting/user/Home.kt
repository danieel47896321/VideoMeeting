package com.example.videomeeting.user

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.adapter.UserAdapter
import com.example.videomeeting.myClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Home : AppCompatActivity() {
    private lateinit var backIcon: ImageView
    private lateinit var title: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var user: User
    private lateinit var recyclerView : RecyclerView
    private var userList = ArrayList<User>()
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }
    private fun init() {
        user = (intent.getSerializableExtra("user") as? User)!!
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
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
        setUsers()
    }
    private fun setUsers() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    for (note in snapshot.children) {
                        val newUser = note.getValue(User::class.java)
                        if (newUser != null) {
                            if(newUser.uid != user.uid)
                                userList.add(newUser)
                        }
                    }
                    showUsers()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun showUsers(){
        val userAdapter = UserAdapter(userList,user)
        recyclerView.layoutManager = GridLayoutManager(this,1)
        recyclerView.adapter = userAdapter
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