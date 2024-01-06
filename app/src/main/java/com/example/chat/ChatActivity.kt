package com.example.chat
/*import MyWebSocketListener
import WebSocketManager*/
import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.os.Build
import android.os.Bundle
import java.time.LocalDateTime
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.chat.data.FileUtils
import com.example.chat.data.Message
import com.example.chat.data.UserManager
import com.example.chat.databinding.ActivityHomeBinding
import com.example.chat.databinding.FragmentHomeBinding
import com.example.chat.databinding.MessageBoxBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class ChatActivity : AppCompatActivity(),WebSocketCallback {

    private val client = OkHttpClient()

    // Здесь укажите URL вашего WebSocket сервера
    private val webSocketUrl = "ws://78.137.17.160:25565"

    private lateinit var webSocket: WebSocket


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        initWebSocket()
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)





        binding.appBarMain.fab.setOnClickListener { view ->
            val messageText = findViewById<EditText>(R.id.message_input)//.text.toString()
            if (messageText.toString().trim().isEmpty()) {
                Snackbar.make(view, "The message is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                var message = Message(UserManager.currentUser!!.username,"${LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm"))}",messageText.text.toString().trim())
                webSocket.send(message.toJson())//sendMessage(messageText)
                messageText.text.clear()
            }
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }




    private fun initWebSocket() {
        val request = Request.Builder().url(webSocketUrl).build()
        webSocket = client.newWebSocket(request, MyWebSocketListener())
    }

    inner class MyWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {

        }

        override fun onMessage(webSocket: WebSocket, text: String) {

            runOnUiThread {

                processWebSocketMessage(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {

        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

        }
    }

    private fun processWebSocketMessage(message: String) {
        var message = Message.fromJson(message)
        createMessageBox(message)
    }















    fun createMessageBox(message: Message){
        val messageBoxCardView = layoutInflater.inflate(R.layout.message_box, null) as CardView
        val chatLayout: LinearLayout = findViewById(R.id.chat_layout)

        val userNameTextView: TextView = messageBoxCardView.findViewById(R.id.user_name)
        val textMessageTextView: TextView = messageBoxCardView.findViewById(R.id.text_message)
        val timeTextView: TextView = messageBoxCardView.findViewById(R.id.time)


        userNameTextView.text = message.senderMessage
        textMessageTextView.text = message.text
        timeTextView.text = message.time

        chatLayout.addView(messageBoxCardView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onMessageReceived(message: String) {

            runOnUiThread {
                val messageBoxCardView = layoutInflater.inflate(R.layout.message_box, null) as CardView
                val chatLayout: LinearLayout = findViewById(R.id.chat_layout)
                chatLayout.addView(messageBoxCardView)
            }
    }
}