package com.example.videomeeting.userActivity.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.R
import com.example.videomeeting.adapter.HomeAdapter
import com.example.videomeeting.controller.HomeFragmentController
import com.example.videomeeting.model.HomeFragmentModel
import com.example.videomeeting.myClass.User

class HomeFragment : Fragment() {
    private lateinit var homeFragmentModel: HomeFragmentModel
    private lateinit var homeFragmentController: HomeFragmentController
    private lateinit var recyclerView: RecyclerView
    private lateinit var backIcon: ImageView
    private lateinit var title: TextView
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        homeFragmentModel = ViewModelProvider(this)[HomeFragmentModel::class.java]
        homeFragmentController = HomeFragmentController(homeFragmentModel, this)
        recyclerView = myView.findViewById<RecyclerView>(R.id.recyclerView)
        title = myView.findViewById<TextView>(R.id.title)
        backIcon = myView.findViewById<ImageView>(R.id.backIcon)
        backIcon.setImageResource(R.drawable.signout)
        backIcon.visibility = View.GONE
        homeFragmentController.setUsers()
        return myView
    }
    fun showUsers(users: ArrayList<User>) {
        recyclerView.adapter = HomeAdapter(users)
    }
}