package com.example.videomeeting.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.OutgoingCall

class HomeFragmentAdapter(private var list: ArrayList<User>, private var context: Context?) : RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_view,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: HomeFragmentAdapter.ViewHolder, position: Int) {
        val user = list[position]
        holder.textViewFullName.text = "${user.firstName} ${user.lastName}"
        if (user.image != "none" && context != null) {
            Glide.with(context!!).load(user.image).into(holder.imageViewUser)
        }
        if (list[position].status == "Online") {
            holder.imageViewStatus.setImageResource(R.drawable.online)
        } else {
            holder.imageViewStatus.setImageResource(R.drawable.offline)
        }
        holder.imageViewCall.setOnClickListener {
            outGoingCall(holder, "Call", user)
        }
        holder.imageViewVideo.setOnClickListener {
            outGoingCall(holder,"Video", user)
        }
    }
    private fun outGoingCall(holder: HomeFragmentAdapter.ViewHolder, type: String, destUser: User){
        val intent = Intent(holder.itemView.context, OutgoingCall::class.java)
        intent.putExtra("type", type)
        intent.putExtra("destUser", destUser)
        holder.itemView.context.startActivity(intent)
        (holder.itemView.context as Activity).finish()
    }
    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textViewFullName: TextView
        var imageViewCall: ImageView
        var imageViewVideo: ImageView
        var imageViewUser: ImageView
        var imageViewStatus: ImageView
        init {
            textViewFullName = view.findViewById(R.id.textViewFullName)
            imageViewCall = view.findViewById(R.id.imageViewCall)
            imageViewUser = view.findViewById(R.id.imageViewUser)
            imageViewVideo = view.findViewById(R.id.imageViewVideo)
            imageViewStatus = view.findViewById(R.id.imageViewStatus)
        }
    }
}