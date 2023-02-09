package com.example.videomeeting.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragmentModel: ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val galleryPermissionCode = 101
    private val cameraPermissionCode = 102
    private var uri: Uri? = null
    fun getAuth(): FirebaseAuth { return firebaseAuth}
    fun getData(): DatabaseReference { return databaseReference }
    fun getCameraPermissionCode(): Int { return cameraPermissionCode }
    fun getGalleryPermissionCode(): Int { return galleryPermissionCode }
    fun getUri(): Uri? { return uri }
    fun setUri(uri: Uri){ this.uri = uri }
}