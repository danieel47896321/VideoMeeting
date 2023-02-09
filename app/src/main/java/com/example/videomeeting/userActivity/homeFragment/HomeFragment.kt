package com.example.videomeeting.userActivity.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.R
import com.example.videomeeting.adapter.HomeFragmentAdapter
import com.example.videomeeting.controller.HomeFragmentController
import com.example.videomeeting.model.HomeFragmentModel
import com.example.videomeeting.myClass.User

class HomeFragment : Fragment() {
    private lateinit var homeFragmentModel: HomeFragmentModel
    private lateinit var homeFragmentController: HomeFragmentController
    private lateinit var recyclerView: RecyclerView
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        homeFragmentModel = ViewModelProvider(this)[HomeFragmentModel::class.java]
        homeFragmentController = HomeFragmentController(homeFragmentModel, this)
        recyclerView = myView.findViewById<RecyclerView>(R.id.recyclerView)
        homeFragmentController.setUsers()
        return myView
    }
    fun showUsers(users: ArrayList<User>) {
        var context = view?.context
        recyclerView.adapter = HomeFragmentAdapter(users, context)
    }
}