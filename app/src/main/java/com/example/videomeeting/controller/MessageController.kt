package com.example.videomeeting.controller

import com.example.videomeeting.model.MessageModel
import com.example.videomeeting.myClass.User
import com.example.videomeeting.userActivity.Message

class MessageController(private var messageModel: MessageModel, private var view: Message) {
    fun setInfo(user: User) {
        messageModel.setUser(user)
        view.setUserInfo("${user.firstName} ${user.lastName}", user.image)

    }
}