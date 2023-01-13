package com.example.videomeeting.guestActivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.videomeeting.R
import com.example.videomeeting.controller.VideoMeetingController
import com.example.videomeeting.model.VideoMeetingModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class VideoMeeting : AppCompatActivity() {
    private lateinit var videoMeetingModel: VideoMeetingModel
    private lateinit var videoMeetingController: VideoMeetingController
    private lateinit var title: TextView
    private lateinit var backIcon: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_meeting)
        init()
    }
    private fun init(){
        videoMeetingModel = ViewModelProvider(this)[VideoMeetingModel::class.java]
        videoMeetingController = VideoMeetingController(videoMeetingModel, this)
        title = findViewById<TextView>(R.id.title)
        backIcon = findViewById<ImageView>(R.id.backIcon)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)
        videoMeetingController.setView()
    }
    fun setHeader(text: String, view: Int) {
        title.text = text
        backIcon.visibility = view
    }
    fun setPager(pagerAdapter: VideoMeetingController.ViewPagerAdapter, titles: Array<String?>) {
        viewPager2.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()
    }
}