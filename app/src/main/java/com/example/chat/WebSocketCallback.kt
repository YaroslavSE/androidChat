package com.example.chat

interface WebSocketCallback {
    fun onMessageReceived(message: String)
}