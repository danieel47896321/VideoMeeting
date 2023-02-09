package com.example.videomeeting.myClass

import java.io.Serializable

class User: Serializable {
    var uid = "Uid"
    var token = "Token"
    var email = "test@email.com"
    var firstName = "FirstName"
    var lastName = "LastName"
    var status = "Offline"
    var image = "none"
    constructor() {}
    constructor(email: String, firstName: String, lastName: String) {
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
    }
}