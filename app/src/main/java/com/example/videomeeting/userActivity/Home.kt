package com.example.videomeeting.userActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.videomeeting.R
import com.example.videomeeting.controller.HomeController
import com.example.videomeeting.guestActivity.VideoMeeting
import com.example.videomeeting.model.HomeModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var homeModel: HomeModel
    private lateinit var homeController: HomeController
    private lateinit var navController: NavController
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }
    private fun init() {
        Log.d("here", "here4")
        homeModel = ViewModelProvider(this)[HomeModel::class.java]
        homeController = HomeController(homeModel, this)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        fragmentContainerView = findViewById<FragmentContainerView>(R.id.fragmentContainerView)
        navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        homeController.setStatus()
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.SignOut))
            .setMessage(resources.getString(R.string.AreYouSure)).setCancelable(true).setPositiveButton(resources.getString(R.string.Yes)) { _, _ ->
                homeController.signOut()
                startActivity(Intent(this, VideoMeeting::class.java))
                finish()
            }.setNegativeButton(resources.getString(R.string.No)) { _, _ -> }.show()
    }
}