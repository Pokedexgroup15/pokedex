package com.example.pokedex.domain

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

class FloatArrayConverter {
    @TypeConverter
    fun stringToListOfStrings(value: Float): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value.toString(), listType)
    }
}
