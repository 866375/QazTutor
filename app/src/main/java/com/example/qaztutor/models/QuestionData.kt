package com.example.qaztutor.models

import com.google.gson.annotations.SerializedName

data class QuestionData (
    var id:Int,
    var question:String,

    var option_one:String,
    var option_two:String,
    var option_three:String,
    var option_four:String,
    @SerializedName("correct_answer")
    var correct_ans:Int
)