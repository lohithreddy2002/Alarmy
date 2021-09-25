package com.example.gowow.db.entity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.gowow.service.Brodcastservice
import com.google.android.material.snackbar.Snackbar
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
@Entity(tableName = "alarms")
@TypeConverters(DaysConverter::class)
data class Alarm(
    @PrimaryKey
    var alarmId: Int,
    var hour: Int,
    var minute: Int,
    var title: String?,
    var started: Boolean,
    var recurring: Boolean,
    var snoozetime: Int = 0,
    var typingwords: Int = 0,
    var stepsCount: Int = 0,
    var shakeCount: Int = 0,
    var days: ArrayList<Boolean>
) : Parcelable {

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Brodcastservice::class.java)
        intent.putExtra("Id", alarmId)
        intent.putExtra("RECURRING", recurring)
        intent.putExtra("TITLE", title)
        intent.putExtra("SNOOZETIME", snoozetime)
        intent.putExtra("DAYS", days)
        intent.putExtra("WORDS", typingwords)
        intent.putExtra("STEPS", stepsCount)
        intent.putExtra("SHAKE", shakeCount)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }
        if (!recurring) {
            var toastText: String? = null
            try {
                toastText =
                    "One Time Alarm ${title} scheduled for ${calendar.get(Calendar.DAY_OF_WEEK)} at ${hour}:${minute} and id is $alarmId"


            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val toastText =
                "Recurnig Alarm ${title} scheduled for ${calendar.get(Calendar.DAY_OF_WEEK)} at ${hour}:${minute} and id is $alarmId"
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }
    }


    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Brodcastservice::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        started = false
        val toastText =
            String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }
}


class DaysConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Boolean> {
        val al = ArrayList<Boolean>()
        value.split("").forEach { al.add(it == "1") }
        al.removeAt(8)
        al.removeAt(0)

        return al
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Boolean>): String {
        return list.joinToString("") { if (it) "1" else "0" }
    }
}