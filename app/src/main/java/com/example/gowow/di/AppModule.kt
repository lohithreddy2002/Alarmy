package com.example.gowow.di

import android.content.Context
import androidx.room.Room
import com.example.gowow.db.database.ReminderDatabase
import com.example.gowow.reminderDoa
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context):ReminderDatabase{
return  Room.databaseBuilder(context.applicationContext,ReminderDatabase::class.java,"Reminder.db").build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(reminderDatabase: ReminderDatabase):reminderDoa = reminderDatabase.getdao()


}