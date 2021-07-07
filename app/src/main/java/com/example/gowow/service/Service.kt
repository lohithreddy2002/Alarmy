package com.example.gowow.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.gowow.RemViewModel
import java.util.*


class Brodcastservice : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "reciervr", Toast.LENGTH_SHORT).show()
        val snz = intent!!.getIntExtra("SNOOZETIME", 0)

        if (!intent.getBooleanExtra("RECURRING", false)) {
            startAlarmService(context, intent, snz)


        } else {
            if (alaramisToday(intent)) {
                startAlarmService(context, intent, snz)
            }
        }


    }

    private fun alaramisToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        val a = System.currentTimeMillis()
        calendar.timeInMillis = a
        val days = intent.getSerializableExtra("DAYS") as ArrayList<Boolean>
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                if (days[0]) {
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {
                if (days[1]) {
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                if (days[2]) {
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                if (days[3]) {
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                if (days[4]) {
                    return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                if (days[5]) {
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                if (days[6]) {
                    return true
                }
                return false
            }

        }

        return false
    }

    private fun startAlarmService(context: Context, intent: Intent, snz: Int) {
        val intentService = Intent(context, NotificationSerivce::class.java)
        intentService.putExtra("SNOOZETIME", snz)
        Log.d("steps", "${intent.getIntExtra("STEPS", 0)}")

        intentService.putExtra("STEPS", intent.getIntExtra("STEPS", 0))
        intentService.putExtra("WORDS", intent.getIntExtra("WORDS", 0))
        intentService.putExtra("SHAKE", intent.getIntExtra("SHAKE", 0))

//    intentService.putExtra(TITLE, intent.getStringExtra(TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

}




