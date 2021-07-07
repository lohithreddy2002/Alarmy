package com.example.gowow

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gowow.factory.RemFactory
import java.lang.Math.abs

class shakingActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    lateinit var viewModel: RemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        val databse = ReminderDatabase(this)
        val repo = Remrepository(databse)
        val factory = RemFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(RemViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shaking)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    var shakecount = 0
    var lasty: Float = 0F
    var lastz: Float = 0F
    var lastx: Float = 0F
    var lastupdate: Long = 0

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val curr = System.currentTimeMillis()

            if (curr - lastupdate > 100) {

                val x_a = event.values[0]
                val y_a = event.values[1]
                val z_a = event.values[2]


                val diftime = System.currentTimeMillis() - lastupdate
                lastupdate = curr

                val speed = abs(x_a + y_a + z_a - (lastx + lasty + lastz)) / diftime * 10000
                if (speed > 800) {
                    viewModel.updateshakecount()
                    Log.d("shakes", "shake count = $shakecount")
                    findViewById<TextView>(R.id.shakescount).text = shakecount.toString()
                }

                lastx = x_a
                lasty = y_a
                lastz = z_a

                if (viewModel.shakecount > intent.getIntArrayExtra("tocomp") as Int) {
                    viewModel.resetshakecount()
                    finish()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //
    }

    override fun onStart() {
        super.onStart()
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        } else {

            Toast.makeText(this, "no sensor", Toast.LENGTH_SHORT).show()
            finish()
        }
        findViewById<TextView>(R.id.shakescount).text = intent.getIntArrayExtra("tocomp").toString()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}