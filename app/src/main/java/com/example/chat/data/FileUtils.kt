package com.example.chat.data

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

object FileUtils {

    fun saveUser(user:User, context: Context){
        val FILENAME = "user_data.json"
        val data = user.toJson()
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            OutputStreamWriter(it).use { writer -> writer.write(data) }
        }
    }

    fun readUser(context: Context):User?{
        val FILENAME = "user_data.json"
        return try{
            val fileInputStream = context.openFileInput(FILENAME)
            val inputSteamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputSteamReader)
            val data = bufferedReader.readLine()
            User.fromJson(data)
        }catch (e: FileNotFoundException){
            null
        }
    }


}