package com.example.videomeeting.guest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.myClass.Loading
import com.example.videomeeting.myClass.PopUpMSG
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    private lateinit var buttonFinish: Button
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var backIcon: ImageView
    private lateinit var title: TextView
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var loading: Loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        init()
    }
    private fun init() {
        backIcon = findViewById<ImageView>(R.id.backIcon)
        title = findViewById<TextView>(R.id.title)
        title.setText(R.string.ResetPassword)
        buttonFinish = findViewById<Button>(R.id.buttonFinish)
        textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        backIcon()
        endIcon()
        resetPasswordButton()
    }
    private fun backIcon() { backIcon.setOnClickListener{ myStartActivity(MainActivity::class.java) } }
    override fun onBackPressed() { myStartActivity(MainActivity::class.java) }
    private fun myStartActivity(Destination: Class<*>) {
        startActivity(Intent(this, Destination))
        finish()
    }
    private fun endIcon(){ textInputLayoutEmail.setEndIconOnClickListener{ clearText(textInputLayoutEmail) } }
    private fun clearText(input: TextInputLayout) {
        input.helperText = ""
        input.editText!!.setText("")
    }
    private fun isEmailValid(email: String?): Boolean { return Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    private fun resetPasswordButton() {
        buttonFinish.setOnClickListener{
            if(textInputLayoutEmail.editText?.text?.length!! > 0) {
                if(!isEmailValid(textInputLayoutEmail.editText?.text.toString()))
                    textInputLayoutEmail.helperText = resources.getString(R.string.InvalidEmail)
                else{
                    loading = Loading(this)
                    firebaseAuth.fetchSignInMethodsForEmail(textInputLayoutEmail.editText?.text.toString()).addOnCompleteListener { task ->
                        loading.stop()
                        if(task.isSuccessful) {
                            if(task.result.signInMethods!!.isNotEmpty()) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(textInputLayoutEmail.editText?.text.toString())
                                PopUpMSG(this, resources.getString(R.string.ResetPassword), resources.getString(R.string.ResetLink), MainActivity::class.java)
                            }else
                                textInputLayoutEmail.helperText = resources.getString(R.string.EmailNotExist)
                        }else
                            PopUpMSG(this, resources.getString(R.string.Error), resources.getString(R.string.ErrorMSG))
                    }
                }
            }else
                textInputLayoutEmail.helperText = resources.getString(R.string.Required)
        }
    }
}