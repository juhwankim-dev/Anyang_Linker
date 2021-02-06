package com.example.anyang_linker.fragments.setting.newgroup

class GroupDTO {
    var leaderUID: String? = null
    var subject: String? = null
    var place: String? = null
    var department: String? = null
    var date: String? = null
    var grade: String? = null
    var people: String? = null
    var type: String? = null
    var introduce: String? = null
    var introduce_date: String? = null
    var introduce_place: String? = null
    var introduce_leader: String? = null
    var title: String? = null

    constructor() {}
    constructor(leaderUID: String?, subject: String?, place: String?, department: String?, date: String?, grade: String?, people: String?, type: String?,
                introduce: String?, introduce_date: String?, introduce_place: String?, introduce_leader: String?, title: String?) {
        this.leaderUID = leaderUID
        this.subject = subject
        this.place = place
        this.department = department
        this.date = date
        this.grade = grade
        this.people = people
        this.type = type
        this.introduce = introduce
        this.introduce_date = introduce_date
        this.introduce_place = introduce_place
        this.introduce_leader = introduce_leader
        this.title = title
    }
}