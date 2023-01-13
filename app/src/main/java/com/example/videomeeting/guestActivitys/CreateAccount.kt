package com.example.videomeeting.guestActivitys
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.videomeeting.myClass.Loading
import com.example.videomeeting.myClass.PopUpMSG
import com.example.videomeeting.myClass.User
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccount : Fragment() {
    private lateinit var textInputLayoutFirstName: TextInputLayout
    private lateinit var textInputLayoutLastName:TextInputLayout
    private lateinit var textInputLayoutEmail:TextInputLayout
    private lateinit var textInputLayoutPassword:TextInputLayout
    private lateinit var textInputLayoutPasswordConfirm:TextInputLayout
    private lateinit var buttonFinish: Button
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private var user: User = User()
    private lateinit var loading :Loading
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_create_account, container, false)
        textInputLayoutFirstName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutFirstName)
        textInputLayoutLastName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutLastName)
        textInputLayoutEmail = myView.findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = myView.findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        textInputLayoutPasswordConfirm = myView.findViewById<TextInputLayout>(R.id.textInputLayoutPasswordConfirm)
        buttonFinish = myView.findViewById<Button>(R.id.buttonNext)
        signOut()
        endIcon()
        createAccountCheck()
        return myView
    }
    private fun signOut() {
        if(firebaseAuth.currentUser != null)
            FirebaseAuth.getInstance().signOut()
    }
    private fun endIcon(){
        textInputLayoutFirstName.setEndIconOnClickListener{ clearText(textInputLayoutFirstName) }
        textInputLayoutLastName.setEndIconOnClickListener { clearText(textInputLayoutLastName) }
        textInputLayoutEmail.setEndIconOnClickListener{ clearText(textInputLayoutEmail) }
    }
    private fun clearText(input: TextInputLayout) {
        input.helperText = ""
        input.editText!!.setText("")
    }
    private fun createAccountCheck(){
        buttonFinish.setOnClickListener{
            checkValues()
            if(textInputLayoutFirstName.editText?.text?.isEmpty() == false && textInputLayoutLastName.editText?.text?.isEmpty() == false && textInputLayoutEmail.editText?.text?.isEmpty() == false
                && isEmailValid(textInputLayoutEmail.editText?.text?.toString()) && textInputLayoutPassword.editText?.text?.isEmpty() == false && textInputLayoutPassword.editText?.text?.length!! > 5
                && textInputLayoutPasswordConfirm.editText?.text?.isEmpty() == false && textInputLayoutPasswordConfirm.editText?.text?.length!! > 5){
                if(textInputLayoutPassword.editText?.text?.toString() != textInputLayoutPasswordConfirm.editText?.text?.toString()){
                    textInputLayoutPasswordConfirm.helperText = resources.getString(R.string.InvalidPassword)
                }else{
                    loading = Loading(myView.context)
                    firebaseAuth.fetchSignInMethodsForEmail( textInputLayoutEmail.editText!!.text.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result.signInMethods!!.isNotEmpty()) {
                                loading.stop()
                                textInputLayoutEmail.helperText = resources.getString(R.string.EmailExist)
                            } else {
                                textInputLayoutEmail.helperText = ""
                                finishCreateAccount()
                            }
                        } else {
                            loading.stop()
                            PopUpMSG(myView.context, resources.getString(R.string.Error), resources.getString(R.string.ErrorMSG))
                        }
                    }
                }
            }
        }
    }
    private fun finishCreateAccount(){
        firebaseAuth.createUserWithEmailAndPassword(textInputLayoutEmail.editText?.text.toString(), textInputLayoutPassword.editText?.text.toString()).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                firebaseAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    user = User(
                        textInputLayoutEmail.editText?.text.toString(),
                        textInputLayoutFirstName.editText?.text.toString(),
                        textInputLayoutLastName.editText?.text.toString()
                    )
                    user.uid = firebaseAuth.uid.toString()
                    databaseReference.child(firebaseAuth.currentUser!!.uid).setValue(user)
                    loading.stop()
                    PopUpMSG( myView.context, resources.getString(R.string.CreateAccount), resources.getString(R.string.CompleteCreateAccount), MainActivity::class.java)
                }
            }else{
                loading.stop()
                PopUpMSG(myView.context, resources.getString(R.string.Error), resources.getString(R.string.ErrorMSG))
            }
        }
    }
    private fun checkValues() {
        if(textInputLayoutFirstName.editText?.text?.isEmpty() == true)
            textInputLayoutFirstName.helperText = resources.getString(R.string.Required)
        else
            textInputLayoutFirstName.helperText = ""
        if (textInputLayoutLastName.editText?.text?.isEmpty() == true)
            textInputLayoutLastName.helperText = resources.getString(R.string.Required)
        else
            textInputLayoutLastName.helperText = ""
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
        if(textInputLayoutPasswordConfirm.editText?.text?.isEmpty() == true)
            textInputLayoutPasswordConfirm.helperText = resources.getString(R.string.Required)
        else if(textInputLayoutPasswordConfirm.editText?.text?.length!! < 6)
            textInputLayoutPasswordConfirm.helperText = resources.getString(R.string.MinimumChars)
        else
            textInputLayoutPasswordConfirm.helperText = ""
    }
    private fun isEmailValid(email: String?): Boolean { return Patterns.EMAIL_ADDRESS.matcher(email).matches() }
}