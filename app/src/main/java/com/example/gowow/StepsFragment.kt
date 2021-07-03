package com.example.gowow

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gowow.databinding.FragmentStepsBinding
import kotlin.math.sqrt

class StepsFragment : AppCompatActivity(),SensorEventListener {

private lateinit var sensorManager: SensorManager
private  var sensor: Sensor? =null
    private lateinit var binding: FragmentStepsBinding
    override fun onCreate(
        savedInstanceState: Bundle?
    ){
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Log.d("sensr","sensmang : $sensorManager")
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        Log.d("sensor","$sensor")
        binding = FragmentStepsBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
    var mag_prev:Double = 0.0
    var count = 0

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x_a = event.values[0]
            val y_a =  event.values[1]
            val z_a = event.values[2]

            val mag = sqrt((x_a*x_a + y_a * y_a + z_a*z_a).toDouble())
            val delta = mag-mag_prev
            mag_prev = mag
            if(delta>6){
                count++
                Log.d("sensor","cout = $count")
            }
            if(count==20){
                setResult(RESULT_OK)
                finish()
            }

           Log.d("sensorvalue","${event.values}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onStart() {
        super.onStart()
        if(sensor != null){
            Log.d("sensorvalue","sensir")
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)

        }
        else{
            Toast.makeText(this, "no sensor", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
            Log.d("sensorvalue","Fsensir")

        }
        binding.stepscomplete.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}