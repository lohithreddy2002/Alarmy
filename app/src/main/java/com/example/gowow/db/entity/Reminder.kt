package com.example.gowow.db.entity

import android.app.PendingIntent
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Reminders Table")
data class Reminder(
    @PrimaryKey
    val ReminderId :Int,
    val ReminderTime :String,
    var ReminderActive:Boolean= false,
//    val a :PendingIntent

)
