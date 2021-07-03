package com.example.gowow.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gowow.*
import com.example.gowow.factory.RemFactory

class NotificationSerivce : Service() {
    private lateinit var vib: Vibrator
    override fun onCreate() {
        super.onCreate()

        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent1 = Intent(this, AlaramDismissActivity::class.java)
        Log.d("snoozetime","${intent?.getIntExtra("SNOOZETIME",0)}")
        val snz = intent?.getIntExtra("SNOOZETIME",0)
        Log.d("snoozetime","$snz")
        intent1.putExtra("S",snz)
        val p = PendingIntent.getActivity(this, 0, intent1,0)
        val notification =
            NotificationCompat.Builder(this, "1").setContentTitle("notificationTitle")
                .setContentText("alarama")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(p)
                .build()

        startForeground(1, notification)
    

        val array = longArrayOf(0, 100, 1000)
        vib.vibrate(VibrationEffect.createWaveform(array, 0))

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        vib.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
