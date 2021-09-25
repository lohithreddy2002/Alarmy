package com.example.gowow

import com.example.gowow.db.database.ReminderDatabase
import com.example.gowow.db.entity.Alarm
import javax.inject.Inject

class ReminderRepository @Inject constructor (private val a: ReminderDatabase) {
    suspend fun insert(item: Alarm) = a.getdao().insertreminder(item)
    suspend fun delete(item: Alarm) = a.getdao().Delete(item)

    fun getAll() = a.getdao().getall()

    suspend fun update(item: Alarm) = a.getdao().update(item)

    fun getitem(id:Int) = a.getdao().getselect(id)
}