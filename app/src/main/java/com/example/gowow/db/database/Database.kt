package com.example.gowow

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gowow.db.entity.Alarm
import com.example.gowow.db.entity.Reminder

@Database(
    entities = [ Alarm::class],
    version = 1
)
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