package com.example.gowow.service

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.gowow.RemViewModel
import com.example.gowow.ReminderDatabase
import com.example.gowow.Remrepository
import com.example.gowow.factory.RemFactory
import java.util.*
import kotlin.collections.ArrayList


class Brodcastservice : BroadcastReceiver() {
    lateinit var viewModel: RemViewModel
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "reciervr", Toast.LENGTH_SHORT).show()
        val snz = intent!!.getIntExtra("SNOOZETIME",0)
        Log.d("days", "${intent.getSerializableExtra("DAYS")}")

        if (!intent!!.getBooleanExtra("RECURRING", false)) {
            startAlarmService(context, intent,snz)


        } else {
            if (alaramisToday(intent)) {
                startAlarmService(context, intent,snz)
            }
        }


    }

    private fun alaramisToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        val a = System.currentTimeMillis()
        calendar.timeInMillis = a
        val day = intent.getSerializableExtra("DAYS")
        val days = day to ArrayList<Boolean>()
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                if (intent.getBooleanExtra("SATURDAY", false)) {
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                if (intent.getBooleanExtra("SUNDAY", false)) {
                    return true
                }
                return false
            }

        }

        return false
    }

    private fun startAlarmService(context: Context, intent: Intent,snz:Int) {
        val intentService = Intent(context, NotificationSerivce::class.java)
        intentService.putExtra("SNOOZETIME",snz)
//    intentService.putExtra(TITLE, intent.getStringExtra(TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

}




