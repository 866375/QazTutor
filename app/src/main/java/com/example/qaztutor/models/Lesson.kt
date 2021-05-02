package com.example.qaztutor.models

class Lesson(
    var id: String = "",
    var lesson_id: String = "",
    var title: String = "",
    var type: String = "",
    var completed: Boolean = false,
    var description: String = "",
    var tasks: List<Task> = emptyList(),
)