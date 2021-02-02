package com.example.anyang_linker.fragments.talk

class ChatDTO {
    var userId: String? = null
    var message: String? = null
    var ampm: String? = null
    var dateTime: String? = null

    constructor() {}
    constructor(userName: String?, message: String?, ampm: String?, dateTime: String?) {
        this.userId = userName
        this.message = message
        this.ampm = ampm
        this.dateTime = dateTime
    }
}