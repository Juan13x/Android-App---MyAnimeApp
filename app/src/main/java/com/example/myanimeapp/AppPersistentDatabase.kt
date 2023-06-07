package com.example.myanimeapp

import android.app.Application
import androidx.room.Room
import com.example.myanimeapp.persistent.PersistentDatabaseMyAnime

class AppPersistentDatabase: Application() {

    companion object{
        lateinit var database: PersistentDatabaseMyAnime
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            PersistentDatabaseMyAnime::class.java,
            "my_anime_db"
        ).build()
    }
}