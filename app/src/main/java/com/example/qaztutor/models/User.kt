package com.example.qaztutor.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var email: String? = "",
    var name: String? = "",
    var password: String? = "",
    var current_course: String? = ""
)