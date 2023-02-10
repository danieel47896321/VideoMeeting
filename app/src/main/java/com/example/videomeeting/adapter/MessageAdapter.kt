package com.example.videomeeting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.myClass.Chat
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private var list: ArrayList<Chat>, private var user: User, private var view: Message) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        return if (viewType == 0) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_view_sender, parent, false)
            ViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_view_receiver, parent, false)
            ViewHolder(v)
        }
    }
    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val chat = list[position]
        holder.textViewMessage.text = chat.message
        if (user.image != "none") {
            if (user.image != "none") {
                Glide.with(view).load(user.image).into(holder.imageViewUser)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (list[position].sender == firebaseAuth.currentUser!!.uid)
            return 0
        return 1
    }
    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewMessage: TextView
        var imageViewUser: ImageView
        init {
            textViewMessage = view.findViewById(R.id.textViewMessage)
            imageViewUser = view.findViewById(R.id.imageViewUser)
        }
    }
}