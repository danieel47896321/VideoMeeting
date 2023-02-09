package com.example.videomeeting.controller

import com.example.videomeeting.model.HomeFragmentModel
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.homeFragment.HomeFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragmentController(var homeFragmentModel: HomeFragmentModel, var view: HomeFragment) {
    fun setUsers() {
        homeFragmentModel.getData().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                homeFragmentModel.getUsers().clear()
                if (snapshot.exists()) {
                    for (user in snapshot.children) {
                        val theUser = user.getValue(User::class.java)
                        if (theUser != null) {
                            if (theUser.uid != homeFragmentModel.getAuth().uid ) {
                                if (view.isSearchTextEmpty()){
                                    homeFragmentModel.getUsers().add(theUser)
                                } else if(theUser.firstName.toLowerCase().contains(view.searchText().toLowerCase()) || theUser.lastName.toLowerCase().contains(view.searchText().toLowerCase())) {
                                    homeFragmentModel.getUsers().add(theUser)
                                }
                            }
                        }
                    }
                    view.showUsers(homeFragmentModel.getUsers())
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}