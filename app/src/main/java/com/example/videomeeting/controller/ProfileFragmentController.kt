package com.example.videomeeting.controller

import android.widget.Toast
import com.example.videomeeting.R
import com.example.videomeeting.model.ProfileFragmentModel
import com.example.videomeeting.userActivity.homeFragment.ProfileFragment
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.regex.Pattern


class ProfileFragmentController(private var profileFragmentModel: ProfileFragmentModel, private var view: ProfileFragment) {
   fun setInfo() {
       var firebaseUser = profileFragmentModel.getAuth().currentUser
       if (firebaseUser != null) {
           var text = firebaseUser.displayName?.split(" ")
           view.setUserInfo(firebaseUser.email, text?.get(0), text?.get(1), firebaseUser.photoUrl)
       }
   }
    fun updateProfile(firstName: String, lastName: String) {
        if (checkInput(firstName, lastName)) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName("$firstName $lastName").build()
            profileFragmentModel.getAuth().currentUser!!.updateProfile(userProfileChangeRequest)
            profileFragmentModel.getData().child(profileFragmentModel.getAuth().currentUser!!.uid).child("firstName").setValue(firstName)
            profileFragmentModel.getData().child(profileFragmentModel.getAuth().currentUser!!.uid).child("lastName").setValue(lastName)
            Toast.makeText(view.context, view.resources.getString(R.string.CompleteEditProfile), Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkInput(firstName: String, lastName: String): Boolean {
        var flag = true
        if (firstName.isEmpty()) {
            view.setFirstNameHelper(view.resources.getString(R.string.Required))
            flag = false
        } else if(gotSpace(firstName)) {
            view.setFirstNameHelper(view.resources.getString(R.string.UseOfSpaces))
            flag = false
        } else {
            view.setFirstNameHelper("")
        }

        if (lastName.isEmpty()) {
            view.setLastNameHelper(view.resources.getString(R.string.Required))
            flag = false
        } else if(gotSpace(lastName)) {
            view.setLastNameHelper(view.resources.getString(R.string.UseOfSpaces))
            flag = false
        } else {
            view.setLastNameHelper("")
        }
        return flag
    }
    private fun gotSpace(name: String): Boolean {
        return name.contains(" ")
    }
}

