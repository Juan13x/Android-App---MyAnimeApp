package com.example.myanimeapp.persistent

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myanimeapp.extensions.ArrayListConverter
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime

@Database(entities = [PersistentEntityMyAnime::class], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class PersistentDatabaseMyAnime: RoomDatabase(){

    abstract fun MyAnimeDao(): MyAnimeDao
}