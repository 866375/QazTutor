package com.example.qaztutor.models

class Course(
    var id: String = "",
    var title: String = "",
    var lessons: List<Lesson> = emptyList(),
    var tasks: List<Task> = emptyList()
)
