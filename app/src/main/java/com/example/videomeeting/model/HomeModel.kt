package com.example.videomeeting.model

import androidx.lifecycle.ViewModel
import com.example.videomeeting.myClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeModel: ViewModel() {
    private var userList = ArrayList<User>()
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun getAuth(): FirebaseAuth { return firebaseAuth}
    fun getData(): DatabaseReference { return databaseReference }
    fun getUsers(): ArrayList<User> { return userList }
}