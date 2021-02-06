package com.example.anyang_linker.Intro

import android.net.Uri

class UserDTO {
    var userName: String? = null
    var userStudentNumber: String? = null
    var userId: String? = null
    var userProfile: String? = null

    constructor() {}
    constructor(userName: String?, userStudentNumber: String?, userId: String?, userProfile: String?) {
        this.userName = userName
        this.userStudentNumber = userStudentNumber
        this.userId = userId
        this.userProfile = userProfile
    }
}