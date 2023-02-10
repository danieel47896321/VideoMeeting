package com.example.videomeeting.controller

import com.example.videomeeting.model.MessageModel
import com.example.videomeeting.myClass.Chat
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class MessageController(private var messageModel: MessageModel, private var view: Message) {
    fun setInfo(user: User) {
        messageModel.setUser(user)
        view.setUserInfo("${user.firstName} ${user.lastName}", user.image)
    }
    fun getSender(): String { return messageModel.getUser().uid}
    fun getReceiver(): String {
        if (messageModel.getAuth().currentUser != null) {
            return messageModel.getAuth().currentUser!!.uid
        }
        return "Error"
    }
    fun sendMessage(chat: Chat) {
        messageModel.getData().push().setValue(chat)
    }
    fun getMessages() {
        var chats = ArrayList<Chat>()
        messageModel.getData().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chats.clear()
                for (dataSnapshot in snapshot.children) {
                    val chat = dataSnapshot.getValue(Chat::class.java)
                    if (chat != null) {
                        if ( (chat.sender == getSender() && chat.receiver == getReceiver()) || (chat.sender == getReceiver() && chat.receiver == getSender()) ) {
                            chats.add(chat)
                        }
                    }
                }
                view.setMessages(chats)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}