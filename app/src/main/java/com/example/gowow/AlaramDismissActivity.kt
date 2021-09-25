package com.example.gowow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.gowow.databinding.ActivityAlaramDismissBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.service.NotificationService
import java.util.*


class AlarmDismissActivity : AppCompatActivity() {

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
        val snztime = i.getIntExtra("S", 0)
        val steps = i.getIntExtra("steps", 0)
        val words = i.getIntExtra("words", 0)
        val shakes = i.getIntExtra("shakes", 0)

        Log.d("taskss", "$snztime")

        Log.d("tasks", "steps = $steps ,shakes = $shakes ,words = $words")



        binding.dismiss.setOnClickListener {
            val intent2 = Intent(this, TypingActivity::class.java)
            val intent1 = Intent(this, StepsFragment::class.java)
            val intent3 = Intent(this, ShakingActivity::class.java)

            if (steps != 0) {
                intent1.putExtra("tocomp", steps)
                previewRequest.launch(intent1)

            } else if (words != 0) {
                intent2.putExtra("tocomp", words)
                previewRequest.launch(intent2)
            } else if (shakes != 0) {
                intent1.putExtra("tocomp", shakes)
                previewRequest.launch(intent3)
            }
//            previewRequest.launch(intent2)
            finish()


        }

        if (snztime == 0) {
            binding.snooze.visibility = View.INVISIBLE
        } else {
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
                    snoozetime = snztime,
                    days = days

                )

                a.schedule(this)
                val intent = Intent(this, NotificationService::class.java)
                this.stopService(intent)
                finish();
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent1 = Intent(this, NotificationService::class.java)
        this.stopService(intent1)

    }


}

