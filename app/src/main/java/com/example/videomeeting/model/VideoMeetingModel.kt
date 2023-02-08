package com.example.videomeeting.model

import androidx.lifecycle.ViewModel
import com.example.videomeeting.R
import com.google.firebase.auth.FirebaseAuth

class VideoMeetingModel: ViewModel() {
    private val titles = intArrayOf(R.string.SignIn, R.string.CreateAccount)
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun getTitles(): IntArray { return titles }
    fun getAuth(): FirebaseAuth { return firebaseAuth }
}