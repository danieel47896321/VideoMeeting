package com.example.videomeeting.guestActivity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.videomeeting.R
import com.example.videomeeting.myClass.Loading
import com.example.videomeeting.myClass.PopUpMSG
import com.example.videomeeting.myClass.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignIn : Fragment() {
    private lateinit var myView: View
    private lateinit var resetPassword: TextView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword:TextInputLayout
    private lateinit var buttonSignIn: Button
    private lateinit var loading: Loading
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        textInputLayoutEmail = myView.findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = myView.findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        buttonSignIn = myView.findViewById<Button>(R.id.buttonSignIn)
        resetPassword = myView.findViewById<TextView>(R.id.resetPassword)
        endIcon()
        signInCheck()
        resetPasswordButton()
        return myView
    }
    private fun endIcon() {
        textInputLayoutEmail.setEndIconOnClickListener{
            textInputLayoutEmail.helperText = ""
            textInputLayoutEmail.editText?.setText("")
        }
    }
    private fun signInCheck() {
        buttonSignIn.setOnClickListener{
            checkValues()
            if(textInputLayoutEmail.editText?.text?.isEmpty() == false && isEmailValid(textInputLayoutEmail.editText?.text?.toString()) &&
                textInputLayoutPassword.editText?.text?.isEmpty() == false && textInputLayoutPassword.editText?.text?.length!! > 5){
                loading = Loading(myView.context)
                signInButton()
            }
        }
    }
    private fun signInButton() {
        firebaseAuth.signInWithEmailAndPassword(textInputLayoutEmail.editText?.text.toString(), textInputLayoutPassword.editText?.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                if(firebaseAuth.currentUser!!.isEmailVerified)
                    getUser()
                else {
                    PopUpMSG(myView.context, resources.getString(R.string.SignIn), resources.getString(R.string.CheckEmailVerify))
                    loading.stop()
                }
            }else
                checkEmailExists()
        }
    }
    private fun getUser() {
        if (firebaseAuth.currentUser != null) {
            databaseReference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    loading.stop()
                    val user = snapshot.getValue(User::class.java)
                    moveToHome(user)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
    private fun moveToHome(user: User?) {
        if (activity != null) {
            val intent = Intent(activity, com.example.videomeeting.userActivitys.Home::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            activity?.finish()
        }
    }
    private fun checkEmailExists() {
        firebaseAuth.fetchSignInMethodsForEmail(textInputLayoutEmail.editText?.text.toString()).addOnCompleteListener { task ->
            loading.stop()
            if(task.isSuccessful) {
                if(task.result.signInMethods!!.isEmpty())
                    textInputLayoutEmail.helperText = resources.getString(R.string.EmailNotExist)
                else {
                    textInputLayoutEmail.helperText = ""
                    if(textInputLayoutPassword.editText?.text?.isEmpty() == true)
                        textInputLayoutPassword.helperText = resources.getString(R.string.Required)
                    else if(textInputLayoutPassword.editText?.text?.length!! < 6)
                        textInputLayoutPassword.helperText = resources.getString(R.string.MinimumChars)
                    else
                        textInputLayoutPassword.helperText = resources.getString(R.string.WrongPassword)
                }
            }else
                PopUpMSG(myView.context, resources.getString(R.string.Error), resources.getString(R.string.ErrorMSG))
        }
    }
    private fun checkValues() {
        if(textInputLayoutEmail.editText?.text?.isEmpty() == true)
            textInputLayoutEmail.helperText = resources.getString(R.string.Required)
        else if(!isEmailValid(textInputLayoutEmail.editText?.text?.toString()))
            textInputLayoutEmail.helperText = resources.getString(R.string.InvalidEmail)
        else
            textInputLayoutEmail.helperText = ""
        if(textInputLayoutPassword.editText?.text?.isEmpty() == true)
            textInputLayoutPassword.helperText = resources.getString(R.string.Required)
        else if(textInputLayoutPassword.editText?.text?.length!! < 6)
            textInputLayoutPassword.helperText = resources.getString(R.string.MinimumChars)
        else
            textInputLayoutPassword.helperText = ""
    }
    private fun isEmailValid(email: String?): Boolean { return Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    private fun resetPasswordButton() {
        resetPassword.setOnClickListener{
            val intent = Intent(activity, ResetPassword::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}