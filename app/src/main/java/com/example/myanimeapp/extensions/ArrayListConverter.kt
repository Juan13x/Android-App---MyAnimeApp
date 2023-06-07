package com.example.myanimeapp.extensions
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayListConverter {
    @TypeConverter
    fun fromArrayList(value: ArrayList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson(value, object : TypeToken<ArrayList<String>>(){}.type) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}