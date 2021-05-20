package com.example.qaztutor.models

import java.io.Serializable

class Course(
    var id: String = "",
    var title: String = "",
    var lessons: List<Lesson> = emptyList(),
    var tasks: List<Task> = emptyList()
) : Serializable
