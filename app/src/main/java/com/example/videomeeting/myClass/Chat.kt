package com.example.videomeeting.myClass

class Chat() {
    lateinit var sender: String
    lateinit var receiver: String
    lateinit var message: String
    lateinit var date: String
    constructor(sender: String, receiver: String, message: String, date: String) : this() {
        this.sender = sender
        this.receiver = receiver
        this.message = message
        this.date = date
    }

}
