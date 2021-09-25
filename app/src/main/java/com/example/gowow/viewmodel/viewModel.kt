package com.example.gowow.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gowow.ReminderRepository
import com.example.gowow.db.entity.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemViewModel @Inject constructor(private val repository: ReminderRepository) : ViewModel() {
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


class SavedStateViewModel(private val state: SavedStateHandle) : ViewModel() {
    fun setLabel(label: String) {
        state.set("LABEL", label)
    }

    fun getLbel(): String? {
        return state.get<String>("LABEL")
    }

    fun setsnztime(time: Int) {
        state.set("SNZTIME", time)
    }

    fun getsnztime(): Int? {
        return state.get<Int>("SNZTIME")
    }

    fun settime(hour: Int, minute: Int) {
        state.set("HOUR", hour)
        state.set("MINUTE", minute)
    }

    fun gettime(): Pair<Int?, Int?> {
        return Pair(state.get<Int>("HOUR"), state.get("MINUTE"))
    }

    private fun settimer(hour: Int, minute: Int) {
        state.set("HOURT", hour)
        state.set("MINUTET", minute)
    }

    fun gettimer(): Pair<Int?, Int?> {
        val hour = state.get<Int>("HOURT")
        val minute = state.get<Int>("MINUTET")
        if (hour != null && minute != null) {

            return Pair(state.get<Int>("HOURT"), state.get("MINUTET"))
        } else {
            if (hour == null && minute == null) {
                return Pair(0, 0)
            } else if (minute == null && hour != null) {
                return Pair(state.get<Int>("HOURT"), 0)
            } else {
                return Pair(0, state.get("MINUTET"))
            }
        }
    }

    fun updatetime(amount: Int) {
        var minute = 0
        var hour = 0
        if (state.contains("HOURT")) {
            hour = state.get<Int>("HOURT")!!
        }
        if (state.contains("MINUTET")) {
            minute = state.get<Int>("MINUTET")!!
        }

        minute += amount
        hour += minute / 60
        minute %= 60
        settimer(hour, minute)
    }

    fun resettimer() {
        state.set("HOURT", 0)
        state.set("MINUTET", 0)

    }


}