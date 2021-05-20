package com.example.qaztutor.models

class Task(
    var id: String = "",
    var course_id: String = "",
    var title: String = "",
    var type: String = "",
    var completed: Boolean = false,
    var description: String = "",
    var question: String = "",
    var answers: List<Answer> = emptyList(),
    var correct_answer: String = ""

)