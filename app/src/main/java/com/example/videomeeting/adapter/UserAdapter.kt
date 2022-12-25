package com.example.videomeeting.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.example.videomeeting.user.IncomingCall
import com.example.videomeeting.user.OutgoingCall

class UserAdapter (noteList: ArrayList<User>, curUser: User) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var list = ArrayList<User>()
    private var user: User
    init {
        list = noteList
        user = curUser
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_view,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.userFullName.text = "${list[position].firstName} ${list[position].lastName}"
        holder.userEmail.text = list[position].email
        holder.imageCall.setOnClickListener { outGoingCall(holder, "Call", list[position].firstName + " " +list[position].lastName) }
        holder.imageVideo.setOnClickListener { outGoingCall(holder,"Video", list[position].firstName + " " +list[position].lastName) }
    }
    private fun outGoingCall(holder: UserAdapter.ViewHolder,type: String, dest: String){
        val intent = Intent(holder.itemView.context, OutgoingCall::class.java)
        intent.putExtra("type", type)
        intent.putExtra("dest", dest)
        intent.putExtra("user", user)
        holder.itemView.context.startActivity(intent)
        (holder.itemView.context as Activity).finish()
    }
    override fun getItemCount(): Int { return list.size }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var userFullName : TextView
        var userEmail : TextView
        var imageCall : ImageView
        var imageVideo : ImageView
        init {
            userFullName = view.findViewById(R.id.userFullName)
            userEmail = view.findViewById(R.id.userEmail)
            imageCall = view.findViewById(R.id.imageCall)
            imageVideo = view.findViewById(R.id.imageVideo)
        }
    }
}