package com.example.videomeeting.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.videomeeting.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class VideoMeeting : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var backIcon: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var titles: Array<String>
    private lateinit var pagerAdapter: PagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_meeting)
        init()
    }
    private fun init(){
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