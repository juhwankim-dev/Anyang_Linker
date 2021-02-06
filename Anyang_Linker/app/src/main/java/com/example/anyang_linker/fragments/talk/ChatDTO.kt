package com.example.anyang_linker.fragments.talk

import android.net.Uri

class ChatDTO {
    var userName: String? = null
    var message: String? = null
    var ampm: String? = null
    var dateTime: String? = null
    var myProfileUrl: String? = null

    constructor() {}
    constructor(userName: String?, message: String?, ampm: String?, dateTime: String?, myProfileUrl: String?) {
        this.userName = userName
        this.message = message
        this.ampm = ampm
        this.dateTime = dateTime
        this.myProfileUrl = myProfileUrl
    }
}