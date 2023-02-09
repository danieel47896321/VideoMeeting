package com.example.videomeeting.model

import androidx.lifecycle.ViewModel
import com.example.videomeeting.myClass.User
import com.google.firebase.auth.FirebaseAuth

class MessageModel: ViewModel() {
    private var user: User = User()
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun getAuth(): FirebaseAuth { return firebaseAuth }
    fun getUser(user: User) { this.user = user }
    fun setUser(user: User): User { return this.user }
}