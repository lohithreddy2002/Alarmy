package com.example.gowow

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.gowow.db.entity.Alarm
import com.example.gowow.db.entity.Reminder

@Dao
interface reminderDoa {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertreminder(item: Alarm)

@Delete
suspend fun Delete(item: Alarm)

    @Query("SELECT * FROM `alarms`")
    fun getall(): LiveData<List<Alarm>>

    @Update
    suspend fun update(item: Alarm)

    @Query("SELECT * FROM 'ALARMS' WHERE alarmId = :a")
    fun getselect(a:Int):Alarm


}