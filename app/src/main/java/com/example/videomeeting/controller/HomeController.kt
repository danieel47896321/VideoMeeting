package com.example.videomeeting.controller

import com.example.videomeeting.model.HomeModel
import com.example.videomeeting.userActivity.Home

class HomeController(var homeModel: HomeModel, var view: Home) {
    fun signOut() {
        if (homeModel.getAuth().currentUser != null) {
            homeModel.getAuth().signOut()
        }
    }
    fun setStatus() {
        if(homeModel.getAuth().uid != null) {
            homeModel.getData().child(homeModel.getAuth().uid!!).child("status").setValue("Online")
            homeModel.getData().child(homeModel.getAuth().uid!!).child("status").onDisconnect().setValue("Offline")
        }
    }
}