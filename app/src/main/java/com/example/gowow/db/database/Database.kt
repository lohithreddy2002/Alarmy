package com.example.gowow

import android.content.Context
import androidx.room.*
import com.example.gowow.db.entity.Alarm
import com.example.gowow.db.entity.DaysConverter
import com.example.gowow.db.entity.Reminder

@Database(
    entities = [Alarm::class],
    version = 1
)
@TypeConverters(DaysConverter::class)
abstract class ReminderDatabase:RoomDatabase() {

    abstract fun getdao():reminderDoa


    companion object{
        @Volatile
        private var instance : ReminderDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: create(context).also {
                instance = it
            }
        }



        private  fun create(context: Context) = Room.databaseBuilder(context.applicationContext,
            ReminderDatabase::class.java,
            "Reminder.db")
            .build()
    }
}