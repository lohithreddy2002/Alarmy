package com.example.gowow

import com.example.gowow.db.entity.Alarm
import com.example.gowow.db.entity.Reminder

class Remrepository(private val a:ReminderDatabase ) {
    suspend fun insert(item: Alarm) = a.getdao().insertreminder(item)
    suspend fun delete(item: Alarm) = a.getdao().Delete(item)

    fun getAll() = a.getdao().getall()

    suspend fun update(item: Alarm) = a.getdao().update(item)

    fun getitem(id:Int) = a.getdao().getselect(id)
}