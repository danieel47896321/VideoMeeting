package com.example.videomeeting.controller

import android.util.Patterns
import android.view.View
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.guestActivity.CreateAccount
import com.example.videomeeting.model.CreateAccountModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class CreateAccountController(var createAccountModel: CreateAccountModel, var view: CreateAccount) {
    fun signOut() {
        if (createAccountModel.getAuth().currentUser != null) {
            createAccountModel.getAuth().signOut()
        }
    }
    private fun checkInput(firstName: String, lastName: String, email: String, password: String, passwordConfirm: String): Boolean {
        var flag = true
        if (firstName.isEmpty()) {
            view.setFirstNameHelper(view.resources.getString(R.string.Required))
            flag = false
        } else {
            view.setFirstNameHelper("")
        }

        if (lastName.isEmpty()) {
            view.setLastNameHelper(view.resources.getString(R.string.Required))
            flag = false
        } else {
            view.setLastNameHelper("")
        }

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

        if (passwordConfirm.isEmpty()) {
            view.setPasswordConfirmHelper(view.resources.getString(R.string.Required))
            flag = false
        } else if (passwordConfirm.length < 6){
            view.setPasswordConfirmHelper(view.resources.getString(R.string.MinimumChars))
            flag = false
        } else if (password != passwordConfirm) {
            view.setPasswordConfirmHelper(view.resources.getString(R.string.InvalidPassword))
            flag = false
        } else {
            view.setPasswordConfirmHelper("")
        }
        return flag
    }
    fun buttonFinish(firstName: String, lastName: String, email: String, password: String, passwordConfirm: String) {
        if(checkInput(firstName, lastName, email, password, passwordConfirm)) {
            view.setProgressBar(View.VISIBLE)
            createAccountModel.getAuth().fetchSignInMethodsForEmail(email).addOnCompleteListener { checkEmailTask ->
                if (checkEmailTask.isSuccessful) {
                    if (checkEmailTask.result.signInMethods!!.isNotEmpty()) {
                        view.setProgressBar(View.GONE)
                        view.setEmailHelper(view.resources.getString(R.string.EmailExist))
                    } else {
                        view.setEmailHelper("")
                        createAccountModel.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener{ createdAccountTask ->
                            if (createdAccountTask.isSuccessful) {
                                createAccountModel.getAuth().currentUser!!.sendEmailVerification().addOnCompleteListener {
                                    val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName("$firstName $lastName").build()
                                    createAccountModel.getAuth().currentUser!!.updateProfile(userProfileChangeRequest)
                                    view.setProgressBar(View.GONE)
                                    createAccountModel.getUser().email = email
                                    createAccountModel.getUser().firstName = firstName
                                    createAccountModel.getUser().lastName = lastName
                                    createAccountModel.getUser().uid = createAccountModel.getAuth().currentUser!!.uid
                                    createAccountModel.getData().child(createAccountModel.getUser().uid).setValue(createAccountModel.getUser())
                                    view.displayMessage(view.resources.getString(R.string.CreateAccount), view.resources.getString(R.string.CompleteCreateAccount), MainActivity::class.java)
                                }
                            } else {
                                view.setProgressBar(View.GONE)
                                view.displayMessage(view.resources.getString(R.string.Error), view.resources.getString(R.string.ErrorMSG), null)
                            }
                        }
                    }
                } else {
                    view.setProgressBar(View.GONE)
                    view.displayMessage(view.resources.getString(R.string.Error), view.resources.getString(R.string.ErrorMSG), null)
                }
            }
        }
    }
    private fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}