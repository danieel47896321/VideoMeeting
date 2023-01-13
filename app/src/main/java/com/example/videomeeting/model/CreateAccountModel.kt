package com.example.videomeeting.model

import androidx.lifecycle.ViewModel
import com.example.videomeeting.myClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private val user: User = User()
    fun getUser(): User { return user }
    fun getAuth(): FirebaseAuth { return firebaseAuth }
    fun getData(): DatabaseReference { return databaseReference }
}