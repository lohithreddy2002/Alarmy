package com.example.gowow

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.gowow.databinding.ActivityAlaramDismissBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.service.NotificationSerivce
import java.util.*
import kotlin.collections.ArrayList


class AlaramDismissActivity : AppCompatActivity() {

    val previewRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                finish()
            }
        }
    private lateinit var binding: ActivityAlaramDismissBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlaramDismissBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }


    override fun onStart() {
        super.onStart()
        val i = intent
        val snztime = i.getIntExtra("S",0)


        binding.dismiss.setOnClickListener {
            val intent2 = Intent(this,Typingactivity::class.java)
            val intent = Intent(this,StepsFragment::class.java)
            previewRequest.launch(intent2)


        }

        if(snztime == 0){
            binding.snooze.visibility = View.INVISIBLE
        }

        else{
        binding.snooze.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.timeInMillis = System.currentTimeMillis()
            cal.add(Calendar.MINUTE, snztime)

            val days = arrayListOf(false, false, false, false, false, false, false)
            val a = Alarm(
                Random().nextInt(Int.MAX_VALUE),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                "Snooze",
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                snztime,
                days

            )

            a.schedule(this)
            val intent = Intent(this, NotificationSerivce::class.java)
            this.stopService(intent)
            finish();
        }



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent1 = Intent(this, NotificationSerivce::class.java)
        this.stopService(intent1)

    }


}

