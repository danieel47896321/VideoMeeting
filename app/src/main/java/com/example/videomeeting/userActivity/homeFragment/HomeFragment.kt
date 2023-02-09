package com.example.videomeeting.userActivity.homeFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
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
    private lateinit var editTextUserSearch: EditText
    private lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        homeFragmentModel = ViewModelProvider(this)[HomeFragmentModel::class.java]
        homeFragmentController = HomeFragmentController(homeFragmentModel, this)
        recyclerView = myView.findViewById<RecyclerView>(R.id.recyclerView)
        editTextUserSearch = myView.findViewById<EditText>(R.id.editTextUserSearch)
        homeFragmentController.setUsers()
        setUserSearch()
        return myView
    }
    private fun setUserSearch() {
        editTextUserSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                homeFragmentController.setUsers()
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
    fun showUsers(users: ArrayList<User>) {
        var context = view?.context
        recyclerView.adapter = HomeFragmentAdapter(users, context)
    }
    fun isSearchTextEmpty(): Boolean {
        return editTextUserSearch.text.toString() == ""
    }
    fun searchText(): String {
        return editTextUserSearch.text.toString()
    }
}