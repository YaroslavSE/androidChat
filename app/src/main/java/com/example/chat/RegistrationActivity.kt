package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.chat.data.FileUtils
import com.example.chat.data.User
import com.example.chat.data.UserManager
import com.example.chat.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enter.setOnClickListener{
            val enteredText:String? = binding.userName.text.toString()
            if(enteredText?.length!! <= 3){
                binding.eror.visibility = View.VISIBLE
            }
            else{

                FileUtils.saveUser(User(enteredText),this)
                UserManager.currentUser = User(enteredText)

                startActivity(Intent(this,ChatActivity::class.java))
                finish()
            }
        }

    }
}