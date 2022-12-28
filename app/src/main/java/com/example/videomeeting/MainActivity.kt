package com.example.videomeeting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.videomeeting.guest.CreateAccount
import com.example.videomeeting.guest.SignIn
import com.example.videomeeting.myClass.Loading
import com.example.videomeeting.myClass.PopUpMSG
import com.example.videomeeting.myClass.User
import com.example.videomeeting.user.Home
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var backIcon: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var titles: Array<String>
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://videomeeting-86807-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.reference.child("Users")
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var pagerAdapter: PagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        if (firebaseAuth.currentUser != null && firebaseAuth.currentUser!!.isEmailVerified) {
            getUser()
        }
        title = findViewById<TextView>(R.id.title)
        title.text = ""
        backIcon = findViewById<ImageView>(R.id.backIcon)
        backIcon.visibility = View.GONE
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)
        titles = arrayOf()
        titles = resources.getStringArray(R.array.Main)
        setPager()
    }
    private fun getUser() {
        if (firebaseAuth.currentUser != null && firebaseAuth.currentUser!!.isEmailVerified) {
            val loading = Loading(this)
            databaseReference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    moveToHome(user)
                    loading.stop()
                }
                override fun onCancelled(error: DatabaseError) { loading.stop() }
            })
        }
    }
    private fun moveToHome(user: User?) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
    private fun setPager() {
        pagerAdapter = PagerAdapter(this)
        viewPager2.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()
    }
    class PagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val size = 2
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SignIn()
                1 -> return CreateAccount()
            }
            return SignIn()
        }
        override fun getItemCount(): Int { return size }
    }
}