package com.example.videomeeting.controller

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.videomeeting.R
import com.example.videomeeting.model.ProfileFragmentModel
import com.example.videomeeting.userActivity.homeFragment.ProfileFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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
            view.setProgressBar(View.VISIBLE)
            val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName("$firstName $lastName").build()
            profileFragmentModel.getAuth().currentUser!!.updateProfile(userProfileChangeRequest)
            profileFragmentModel.getData().child(profileFragmentModel.getAuth().currentUser!!.uid).child("firstName").setValue(firstName)
            profileFragmentModel.getData().child(profileFragmentModel.getAuth().currentUser!!.uid).child("lastName").setValue(lastName)
            updateImage()
        }
    }
    private fun updateImage() {
        val storageReference = FirebaseStorage.getInstance().reference
        val reference: StorageReference = storageReference.child(profileFragmentModel.getAuth().currentUser!!.uid + ".jpg")
        if (profileFragmentModel.getUri() != null) {
            reference.putFile(profileFragmentModel.getUri()!!).addOnCompleteListener(OnCompleteListener { task ->
                task.result.storage.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                    val userProfileChangeRequest = UserProfileChangeRequest.Builder().setPhotoUri(uri).build()
                    profileFragmentModel.getAuth().currentUser!!.updateProfile(userProfileChangeRequest)
                    profileFragmentModel.getData().child(profileFragmentModel.getAuth().currentUser!!.uid).child("image").setValue(uri.toString())
                    msgCompleteEditProfile()
                })
            })
        } else {
            msgCompleteEditProfile()
        }
    }
    private fun msgCompleteEditProfile() {
        if (view.context != null) {
            view.setProgressBar(View.GONE)
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
    fun dialogCamera() {
        val context: Context? = view!!.context
        if (context != null) {
            val builder = AlertDialog.Builder(context)
            val inflater: LayoutInflater = view.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.dialog_camera, null)
            builder.setCancelable(false)
            builder.setView(dialogView)
            val camera: ImageView = dialogView.findViewById<ImageView>(R.id.Camera)
            val gallery: ImageView = dialogView.findViewById<ImageView>(R.id.Gallery)
            val alertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()
            camera.setOnClickListener(View.OnClickListener {
                view.selectedCamera()
                alertDialog.cancel()
            })
            gallery.setOnClickListener {
                view.selectedGallery()
                alertDialog.cancel()
            }
        }
    }
    fun getGalleryPermissionCode(): Int {
        return profileFragmentModel.getGalleryPermissionCode()
    }
    fun getCameraPermissionCode(): Int {
        return profileFragmentModel.getCameraPermissionCode()
    }
    fun setUri(uri: Uri){ profileFragmentModel.setUri(uri) }
    fun getImage(): Uri? {
        var firebaseUser = profileFragmentModel.getAuth().currentUser
        if (firebaseUser != null) {
            return firebaseUser.photoUrl
        }
        return null
    }
}

