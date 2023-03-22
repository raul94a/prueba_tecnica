package raulalbin.prueba.tecnica.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import raulalbin.prueba.tecnica.data.database.dao.CharacterDAO
import raulalbin.prueba.tecnica.data.database.dao.EpisodeDAO
import raulalbin.prueba.tecnica.data.database.dao.LocationDAO
import raulalbin.prueba.tecnica.data.models.Episode
import raulalbin.prueba.tecnica.data.models.Location
import raulalbin.prueba.tecnica.data.models.TvCharacter


@Database(entities = [TvCharacter::class, Location::class, Episode::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase() {

    abstract  fun characterDao(): CharacterDAO
    abstract  fun episodeDao(): EpisodeDAO
    abstract  fun locationDao(): LocationDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            return instance?: synchronized(this){
                instance?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "db")
                .addCallback(
                    object : Callback() {
                    }
                )
                .build()
        }
    }

}