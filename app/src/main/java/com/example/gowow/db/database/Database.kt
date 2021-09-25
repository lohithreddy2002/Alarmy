package com.example.gowow.db.database

import androidx.room.*
import com.example.gowow.db.entity.Alarm
import com.example.gowow.db.entity.DaysConverter
import com.example.gowow.reminderDoa

@Database(
    entities = [Alarm::class],
    version = 1
)
@TypeConverters(DaysConverter::class)
abstract class ReminderDatabase:RoomDatabase() {

    abstract fun getdao(): reminderDoa

}