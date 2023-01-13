package com.example.videomeeting.model

import androidx.lifecycle.ViewModel
import com.example.videomeeting.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainModel: ViewModel(){
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun getData(): DatabaseReference { return databaseReference }
    fun getAuth(): FirebaseAuth { return firebaseAuth }
}