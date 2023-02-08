package com.example.videomeeting.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.OutgoingCall

class HomeAdapter(private var list: ArrayList<User>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_view,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.textViewFullName.text = "${list[position].firstName} ${list[position].lastName}"
        if (list[position].status == "Online") {
            holder.imageViewStatus.setImageResource(R.drawable.online)
        } else {
            holder.imageViewStatus.setImageResource(R.drawable.offline)
        }
        holder.imageViewCall.setOnClickListener {
            outGoingCall(holder, "Call", list[position])
        }
        holder.imageViewVideo.setOnClickListener {
            outGoingCall(holder,"Video", list[position])
        }
    }
    private fun outGoingCall(holder: HomeAdapter.ViewHolder, type: String, destUser: User){
        val intent = Intent(holder.itemView.context, OutgoingCall::class.java)
        intent.putExtra("type", type)
        intent.putExtra("destUser", destUser)
        holder.itemView.context.startActivity(intent)
        (holder.itemView.context as Activity).finish()
    }
    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var textViewFullName : TextView
        var imageViewCall : ImageView
        var imageViewVideo : ImageView
        var imageViewStatus : ImageView
        init {
            textViewFullName = view.findViewById(R.id.textViewFullName)
            imageViewCall = view.findViewById(R.id.imageViewCall)
            imageViewVideo = view.findViewById(R.id.imageViewVideo)
            imageViewStatus = view.findViewById(R.id.imageViewStatus)
        }
    }
}