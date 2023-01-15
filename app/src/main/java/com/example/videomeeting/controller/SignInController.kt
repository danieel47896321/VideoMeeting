package com.example.videomeeting.controller

import android.util.Patterns
import android.view.View
import com.example.videomeeting.R
import com.example.videomeeting.guestActivity.SignIn
import com.example.videomeeting.model.SignInModel
import com.example.videomeeting.myClass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SignInController(var signInModel: SignInModel, var view: SignIn) {
    fun buttonSignIn(email: String, password: String) {
        if (checkInput(email, password)) {
            view.setProgressBar(View.VISIBLE)
            signInModel.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (signInModel.getAuth().currentUser!!.isEmailVerified) {
                        signInModel.getData().child(signInModel.getAuth().currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                view.setProgressBar(View.GONE)
                                val user = snapshot.getValue(User::class.java)
                                view.moveToHome(user)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                view.setProgressBar(View.GONE)
                            }
                        })
                    } else {
                        view.displayMessage(view.resources.getString(R.string.SignIn), view.resources.getString(R.string.CheckEmailVerify), null)
                        view.setProgressBar(View.GONE)
                    }
                } else {
                    signInModel.getAuth().fetchSignInMethodsForEmail(email).addOnCompleteListener { emailExistTask ->
                        if (emailExistTask.isSuccessful) {
                            if (emailExistTask.result.signInMethods!!.isEmpty()) {
                                view.setProgressBar(View.GONE)
                                view.setEmailHelper(view.resources.getString(R.string.EmailNotExist))
                            } else {
                                view.setProgressBar(View.GONE)
                                view.setEmailHelper("")
                                view.setPasswordHelper(view.resources.getString(R.string.WrongPassword))
                            }
                        } else {
                            view.setProgressBar(View.GONE)
                            view.displayMessage(view.resources.getString(R.string.Error), view.resources.getString(R.string.ErrorMSG), null)
                        }
                    }
                }
            }
        }
    }
    private fun checkInput(email: String, password: String): Boolean {
        var flag = true
        if (email.isEmpty()) {
            view.setEmailHelper(view.resources.getString(R.string.Required))
            flag = false
        } else if (!isEmailValid(email)) {
            view.setEmailHelper(view.resources.getString(R.string.InvalidEmail))
            flag = false
        } else {
            view.setEmailHelper("")
        }

        if (password.isEmpty()) {
            view.setPasswordHelper(view.resources.getString(R.string.Required))
            flag = false
        } else if (password.length < 6){
            view.setPasswordHelper(view.resources.getString(R.string.MinimumChars))
            flag = false
        } else {
            view.setPasswordHelper("")
        }

        return flag
    }
    private fun isEmailValid(email: String?): Boolean { return Patterns.EMAIL_ADDRESS.matcher(email).matches() }
}