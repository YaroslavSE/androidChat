package com.example.chat
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.chat.data.FileUtils
import com.example.chat.data.UserManager
import com.example.chat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedUser = FileUtils.readUser(this)

        if(savedUser != null){
            UserManager.currentUser = savedUser

            startActivity(Intent(this, ChatActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this,RegistrationActivity::class.java))
        }



    }


}