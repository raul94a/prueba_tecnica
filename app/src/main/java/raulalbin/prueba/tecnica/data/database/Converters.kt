package raulalbin.prueba.tecnica.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson


class Converters {
    @TypeConverter fun ListToJson(list: List<String>): String = Gson().toJson(list)
    @TypeConverter fun jsonToList(string: String): List<String> = Gson().fromJson(string,Any::class.java) as List<String>
}