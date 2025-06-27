package com.example.Formula.logging

class Logger {
    fun info(message: String) {
        val timestamp = java.time.LocalDateTime.now()
        println("[INFO] [$timestamp] $message")
    }
}