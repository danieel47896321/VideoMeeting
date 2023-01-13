package com.example.videomeeting.controller

import android.util.Patterns
import android.view.View
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.guestActivitys.ResetPassword
import com.example.videomeeting.guestActivitys.VideoMeeting
import com.example.videomeeting.model.ResetPasswordModel
import com.example.videomeeting.myClass.PopUpMSG
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordController(var resetPasswordModel: ResetPasswordModel, var view: ResetPassword) {
    fun setView() {
        val text = resetPasswordModel.getTitle()
        view.setHeader(text)
    }
    fun backIcon() {
        view.startActivity(VideoMeeting::class.java)
    }
    fun endIcon() {
        view.clearText("")
    }
    fun buttonFinish(email: String) {
        if (!isEmailValid(email)) {
            view.setEmailHelperText(view.resources.getString(R.string.InvalidEmail))
        } else {
            view.setProgressBar(View.VISIBLE)
            resetPasswordModel.getAuth().fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.setProgressBar(View.GONE)
                    if (task.result.signInMethods!!.isNotEmpty()) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        val title = view.resources.getString(R.string.ResetPassword)
                        val text = view.resources.getString(R.string.ResetLink)
                        PopUpMSG(view, title, text, MainActivity::class.java)
                    } else {
                        view.setEmailHelperText(view.resources.getString(R.string.EmailNotExist))
                    }
                } else {
                    view.setProgressBar(View.GONE)
                    val title = view.resources.getString(R.string.Error)
                    val text = view.resources.getString(R.string.ErrorMSG)
                    PopUpMSG(view, title, text)
                }
            }
        }
    }
    private fun isEmailValid(email: String?): Boolean { return Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    fun buttonFinish(length: Int, email: String) {
        if(length > 0){
            buttonFinish(email)
        } else {
            view.setEmailHelperText(view.resources.getString(R.string.Required))
        }
    }
}