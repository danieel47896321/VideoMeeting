package com.example.videomeeting.guestActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.videomeeting.MainActivity
import com.example.videomeeting.myClass.PopUpMSG
import com.example.videomeeting.R
import com.example.videomeeting.controller.CreateAccountController
import com.example.videomeeting.model.CreateAccountModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class CreateAccount : Fragment() {
    private lateinit var createAccountModel: CreateAccountModel
    private lateinit var createAccountController: CreateAccountController
    private lateinit var progressBar: ProgressBar
    private lateinit var textInputLayoutFirstName: TextInputLayout
    private lateinit var textInputLayoutLastName:TextInputLayout
    private lateinit var textInputLayoutEmail:TextInputLayout
    private lateinit var textInputLayoutPassword:TextInputLayout
    private lateinit var textInputLayoutPasswordConfirm:TextInputLayout
    private lateinit var buttonFinish: Button
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_create_account, container, false)
        createAccountModel = ViewModelProvider(this)[CreateAccountModel::class.java]
        createAccountController = CreateAccountController(createAccountModel, this)
        textInputLayoutFirstName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutFirstName)
        textInputLayoutLastName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutLastName)
        textInputLayoutEmail = myView.findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = myView.findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        textInputLayoutPasswordConfirm = myView.findViewById<TextInputLayout>(R.id.textInputLayoutPasswordConfirm)
        buttonFinish = myView.findViewById<Button>(R.id.buttonNext)
        progressBar = myView.findViewById<ProgressBar>(R.id.progressBar)
        createAccountController.signOut()
        endIcon()
        createAccountCheck()
        return myView
    }
    private fun endIcon(){
        textInputLayoutFirstName.setEndIconOnClickListener {
            clearText(textInputLayoutFirstName)
        }
        textInputLayoutLastName.setEndIconOnClickListener {
            clearText(textInputLayoutLastName)
        }
        textInputLayoutEmail.setEndIconOnClickListener{
            clearText(textInputLayoutEmail)
        }
    }
    private fun clearText(input: TextInputLayout) {
        input.helperText = ""
        input.editText!!.setText("")
    }
    private fun createAccountCheck(){
        buttonFinish.setOnClickListener {
            createAccountController.buttonFinish(
                textInputLayoutFirstName.editText?.text.toString(),
                textInputLayoutLastName.editText?.text.toString(),
                textInputLayoutEmail.editText?.text.toString(),
                textInputLayoutPassword.editText?.text.toString(),
                textInputLayoutPasswordConfirm.editText?.text.toString()
            )
        }
    }
    fun setFirstNameHelper(text: String) {
        textInputLayoutFirstName.helperText = text
    }
    fun setLastNameHelper(text: String) {
        textInputLayoutLastName.helperText = text
    }
    fun setEmailHelper(text: String) {
        textInputLayoutEmail.helperText = text
    }
    fun setPasswordHelper(text: String) {
        textInputLayoutPassword.helperText = text
    }
    fun setPasswordConfirmHelper(text: String) {
        textInputLayoutPasswordConfirm.helperText = text
    }
    fun setProgressBar(view: Int) {
        progressBar.visibility = view
    }
    fun displayMessage(title: String, text: String, dest: Class<MainActivity>?){
        if (dest == null) {
            PopUpMSG(myView.context, title, text)
        } else {
            PopUpMSG(myView.context, title, text, dest)
        }
    }
}