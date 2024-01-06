package com.example.chat.data

import com.google.gson.Gson

class Message(val senderMessage:String, val time:String, val text:String) {
    fun toJson():String = Gson().toJson(this)

    companion object{
        fun fromJson(json:String):Message = Gson().fromJson(json, Message::class.java)
    }
}