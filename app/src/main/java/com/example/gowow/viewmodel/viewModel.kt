package com.example.gowow

import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gowow.db.entity.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class  RemViewModel(private val repository:Remrepository): ViewModel() {
    fun insert(item: Alarm) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }

    fun delete(item: Alarm) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getall() = repository.getAll()



    fun update(item: Alarm) = CoroutineScope(Dispatchers.Main).launch {
        repository.update(item)
    }


    private fun setAlaram(timeinmills:Long,ala:AlarmManager,Pendingintent:PendingIntent){
        ala.setExact(
            AlarmManager.RTC_WAKEUP,
            timeinmills,
            Pendingintent
        )
        Log.d("setting","alaam set")
    }


    private fun cancelAlaram(alarmManager: AlarmManager, id: Int, pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)

    }

    fun getselectalarm(id: Int) = repository.getitem(id)

    var shakecount = 0

    fun updateshakecount() {
        shakecount++
    }

    fun resetshakecount() {
        shakecount = 0
    }

}