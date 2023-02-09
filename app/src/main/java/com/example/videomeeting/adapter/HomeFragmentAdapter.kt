package com.example.videomeeting.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.IncomingCall
import com.example.videomeeting.userActivity.Message
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
        holder.cardViewUserImage.setOnClickListener {
            val context: Context? = holder!!.itemView.context
            if (context != null) {
                val builder = AlertDialog.Builder(context)
                val inflater: LayoutInflater = LayoutInflater.from(context)
                val dialogView: View = inflater.inflate(R.layout.dialog_large_image, null)
                builder.setCancelable(false)
                builder.setView(dialogView)
                val imageViewLargeImage: ImageView = dialogView.findViewById<ImageView>(R.id.imageViewLargeImage)
                val alertDialog = builder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                if (user.image != "none") {
                    alertDialog.show()
                    Glide.with(context).load(user.image).into(imageViewLargeImage)
                } else {
                    Toast.makeText(context,"${context.getString(R.string.TheUser)} ${user.firstName} ${user.lastName} ${context.getString(R.string.HaveNoImage)}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        holder.constraintLayout.setOnClickListener {
            val intent = Intent(context, Message::class.java)
            intent.putExtra("user", user)
            context!!.startActivity(intent)
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
        var cardViewUserImage: CardView
        var constraintLayout: ConstraintLayout
        init {
            textViewFullName = view.findViewById(R.id.textViewFullName)
            imageViewCall = view.findViewById(R.id.imageViewCall)
            imageViewUser = view.findViewById(R.id.imageViewUser)
            imageViewVideo = view.findViewById(R.id.imageViewVideo)
            cardViewUserImage = view.findViewById(R.id.cardViewUserImage)
            constraintLayout = view.findViewById(R.id.constraintLayout)
            imageViewStatus = view.findViewById(R.id.imageViewStatus)
        }
    }
}