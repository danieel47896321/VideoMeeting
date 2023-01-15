package com.example.videomeeting.controller

import com.example.videomeeting.R
import com.example.videomeeting.model.HomeModel
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivitys.Home
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeController(var homeModel: HomeModel, var view: Home) {
    fun setView() {
        val title = R.string.Home
        val fullName = homeModel.getAuth().currentUser?.displayName
        view.updateView(title, fullName)
        setUsers()
    }
    private fun setUsers() {
        homeModel.getData().addValueEventListener(object : ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                 homeModel.getUsers().clear()
                 if (snapshot.exists()) {
                     for (user in snapshot.children) {
                         val theUser = user.getValue(User::class.java)
                         if (theUser != null) {
                             if (theUser.uid != homeModel.getAuth().uid ) {
                                 homeModel.getUsers().add(theUser)
                             }
                         }
                     }
                     view.showUsers(homeModel.getUsers())
                 }
             }
             override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun signOut() {
        if (homeModel.getAuth().currentUser != null) {
            homeModel.getAuth().signOut()
        }
    }
}