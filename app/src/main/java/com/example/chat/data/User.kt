package com.example.chat.data
import com.google.gson.Gson

data class User(val username:String) {

    fun toJson():String = Gson().toJson(this)

    companion object{
        fun fromJson(json:String):User = Gson().fromJson(json, User::class.java)
    }
}