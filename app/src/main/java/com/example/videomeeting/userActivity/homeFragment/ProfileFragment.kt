package com.example.videomeeting.userActivity.homeFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.controller.ProfileFragmentController
import com.example.videomeeting.model.ProfileFragmentModel
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream


class ProfileFragment : Fragment() {
    private lateinit var profileFragmentModel: ProfileFragmentModel
    private lateinit var profileFragmentController: ProfileFragmentController
    private lateinit var imageViewUserImage: ImageView
    private lateinit var cardViewCamera: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonEditProfile: Button
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutFirstName: TextInputLayout
    private lateinit var textInputLayoutLastName: TextInputLayout
    private lateinit var ccc: ConstraintLayout
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_profile, container, false)
        profileFragmentModel = ViewModelProvider(this)[ProfileFragmentModel::class.java]
        profileFragmentController = ProfileFragmentController(profileFragmentModel, this)
        imageViewUserImage = myView.findViewById<ImageView>(R.id.imageViewUserImage)
        ccc = myView.findViewById<ConstraintLayout>(R.id.test)
        progressBar = myView.findViewById<ProgressBar>(R.id.progressBar)
        cardViewCamera = myView.findViewById<CardView>(R.id.cardViewCamera)
        buttonEditProfile = myView.findViewById<Button>(R.id.buttonEditProfile)
        textInputLayoutEmail = myView.findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutFirstName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutFirstName)
        textInputLayoutLastName = myView.findViewById<TextInputLayout>(R.id.textInputLayoutLastName)
        profileFragmentController.setInfo()
        setButtonEditProfile()
        setButtonCamera()
        setFullScreenImage()
        return myView
    }

    private fun setFullScreenImage() {
        imageViewUserImage.setOnClickListener(View.OnClickListener {
            val context: Context? = myView!!.context
            if (context != null) {
                val builder = AlertDialog.Builder(context)
                val inflater: LayoutInflater = this.layoutInflater
                val dialogView: View = inflater.inflate(R.layout.dialog_large_image, null)
                builder.setCancelable(false)
                builder.setView(dialogView)
                val imageViewLargeImage: ImageView = dialogView.findViewById<ImageView>(R.id.imageViewLargeImage)
                val alertDialog = builder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.show()
                alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                Glide.with(this).load(imageViewUserImage.drawable).into(imageViewLargeImage)
            }
        })
    }
    private fun setButtonCamera() {
        cardViewCamera.setOnClickListener {
            profileFragmentController.dialogCamera()
        }
    }
    private fun setButtonEditProfile() {
        buttonEditProfile.setOnClickListener {
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
            imageViewUserImage.setImageResource(R.drawable.person)
        }
    }
    fun setProgressBar(view: Int) {
        progressBar.visibility = view
    }
    fun setFirstNameHelper(text: String) {
        textInputLayoutFirstName.helperText = text
    }
    fun setLastNameHelper(text: String) {
        textInputLayoutLastName.helperText = text
    }
    fun selectedGallery() {
        val context: Context? = this.context
        if (context != null) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val activity = this.activity
                if (activity != null) {
                    ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), profileFragmentController.getGalleryPermissionCode())
                }
            } else {
                startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), profileFragmentController.getGalleryPermissionCode())
            }
        }
    }
    fun selectedCamera() {
        val context: Context? = this.context
        if (context != null) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                val activity = this.activity
                if (activity != null) {
                    ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), profileFragmentController.getCameraPermissionCode())
                }
            } else {
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), profileFragmentController.getCameraPermissionCode())
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            profileFragmentController.getGalleryPermissionCode() -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.data
                    if (selectedImage != null) {
                        if (view?.context != null) {
                            imageViewUserImage.setImageURI(data.data)
                            profileFragmentController.setUri(selectedImage)
                        }
                    }
                }
            }
            profileFragmentController.getCameraPermissionCode() -> {
                if (resultCode == Activity.RESULT_OK && data != null && this.context != null) {
                    val bitmap = data.extras!!["data"] as Bitmap?
                    val bytes = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path = MediaStore.Images.Media.insertImage(this.requireContext().contentResolver, bitmap, "Title", null)
                    var uri = Uri.parse(path)
                    profileFragmentController.setUri(uri)
                    imageViewUserImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == profileFragmentController.getCameraPermissionCode()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 2)
            }
        }
    }
}