package com.example.videomeeting.userActivity.homeFragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.videomeeting.MainActivity
import com.example.videomeeting.R
import com.example.videomeeting.controller.ProfileFragmentController
import com.example.videomeeting.model.ProfileFragmentModel
import com.example.videomeeting.myClass.PopUpMSG
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {
    private lateinit var profileFragmentModel: ProfileFragmentModel
    private lateinit var profileFragmentController: ProfileFragmentController
    private lateinit var imageViewUserImage: ImageView
    private lateinit var cardViewCamera: CardView
    private lateinit var buttonEditProfile: Button
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutFirstName: TextInputLayout
    private lateinit var textInputLayoutLastName: TextInputLayout
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_profile, container, false)
        profileFragmentModel = ViewModelProvider(this)[ProfileFragmentModel::class.java]
        profileFragmentController = ProfileFragmentController(profileFragmentModel, this)
        imageViewUserImage = myView.findViewById<ImageView>(R.id.imageViewUserImage)
        cardViewCamera = myView.findViewById<CardView>(R.id.cardViewCamera)
        buttonEditProfile = myView.findViewById<Button>(R.id.buttonEditProfile)
        textInputLayoutEmail = myView.findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutFirstName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutFirstName)
        textInputLayoutLastName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutLastName)
        profileFragmentController.setInfo()
        setButtonEditProfile()
        return myView
    }

    private fun setButtonEditProfile() {
        buttonEditProfile.setOnClickListener{ v ->
            profileFragmentController.updateProfile(textInputLayoutFirstName.editText?.text.toString(), textInputLayoutLastName.editText?.text.toString())
        }
    }
    fun setUserInfo(email: String?, firstName: String?, lastName: String?, photoUrl: Uri?) {
        textInputLayoutEmail.editText?.setText(email)
        textInputLayoutFirstName.editText?.setText(firstName)
        textInputLayoutLastName.editText?.setText(lastName)
        if (photoUrl != null ) {
            Glide.with(this).load(photoUrl).into(imageViewUserImage)
        } else {
           // imageViewUserImage.setImageDrawable(R.drawable.person)
        }
    }
    fun displayMessage(title: String, text: String, dest: Class<MainActivity>?) {
        if (dest == null) {
            PopUpMSG(myView.context, title, text)
        } else {
            PopUpMSG(myView.context, title, text, dest)
        }
        textInputLayoutFirstName.helperText = ""
        textInputLayoutLastName.helperText = ""
    }
    fun setFirstNameHelper(text: String) {
        textInputLayoutFirstName.helperText = text
    }
    fun setLastNameHelper(text: String) {
        textInputLayoutLastName.helperText = text
    }
}