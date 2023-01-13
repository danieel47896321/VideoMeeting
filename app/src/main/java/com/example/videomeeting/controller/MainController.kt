package com.example.videomeeting.controller

import com.example.videomeeting.MainActivity
import com.example.videomeeting.model.MainModel
import com.example.videomeeting.myClass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainController(var mainModel: MainModel, var view: MainActivity) {
    fun checkCurrentUser(){
        if (mainModel.getAuth().currentUser != null && mainModel.getAuth().currentUser!!.isEmailVerified) {
            mainModel.getData().child(mainModel.getAuth().currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        view.homePage(user)
                    } else {
                        view.videoMeetingPage()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    view.videoMeetingPage()
                }
            })
        } else {
            view.videoMeetingPage()
        }
    }
}