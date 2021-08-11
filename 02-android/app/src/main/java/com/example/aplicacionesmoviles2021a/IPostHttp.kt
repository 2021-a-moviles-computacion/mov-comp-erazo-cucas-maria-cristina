package com.example.aplicacionesmoviles2021a

import android.icu.text.CaseMap

class IPostHttp(
    val id: Int,
    var userId: Any,
    val title: String,
    var body: String
) {

    init{
        if(userId is String){
            userId= (userId as String).toInt()
        }
        if(userId is Int){
            userId= userId
        }
    }
}