package com.example.videomeeting.controller

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videomeeting.guestActivitys.CreateAccount
import com.example.videomeeting.guestActivitys.SignIn
import com.example.videomeeting.guestActivitys.VideoMeeting
import com.example.videomeeting.model.VideoMeetingModel

class VideoMeetingController(var videoMeetingModel: VideoMeetingModel, var view: VideoMeeting) {
    fun setView(){
        view.setHeader("", View.GONE)
        view.setPager(ViewPagerAdapter(view, videoMeetingModel.getTitles()), getTitles())
    }
    private fun getTitles(): Array<String?> {
        val titleSize = videoMeetingModel.getTitles().size
        val titles = arrayOfNulls<String>(titleSize)
        for(i in 0 until titleSize)
            titles[i] = view.resources.getString(videoMeetingModel.getTitles()[i])
        return titles
    }
    class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val titles: IntArray): FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SignIn()
                1 -> return CreateAccount()
            }
            return SignIn()
        }
        override fun getItemCount(): Int { return titles.size }
    }
}