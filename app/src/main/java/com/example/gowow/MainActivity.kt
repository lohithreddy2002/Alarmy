package com.example.gowow

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class  MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationchannel()
    }


    private fun notificationchannel() {
        val name = "RemiderChannel"
        val descriptionT = "Channel for Remider app"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel_id = "1"

        val channel = NotificationChannel(channel_id, name, importance).apply {
            description = descriptionT
        }

        val nofi: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nofi.createNotificationChannel(channel)
    }

}