package com.example.gowow

import androidx.lifecycle.ViewModel
import com.example.gowow.db.entity.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemViewModel(private val repository: Remrepository) : ViewModel() {
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


    fun getselectalarm(id: Int) = repository.getitem(id)

    var shakecount = 0

    fun updateshakecount() {
        shakecount++
    }

    fun resetshakecount() {
        shakecount = 0
    }


}