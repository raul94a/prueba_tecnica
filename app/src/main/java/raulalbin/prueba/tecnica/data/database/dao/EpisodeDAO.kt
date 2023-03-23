package raulalbin.prueba.tecnica.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import raulalbin.prueba.tecnica.data.models.Episode


@Dao
interface EpisodeDAO {
    @Query("Select * from episodes ORDER BY id")
    suspend fun getEpisodes(): List<Episode>

    @Query("Select * from episodes where url = :url limit 1")
    suspend fun getEpisodeByUrl(url: String): Episode?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(location: Episode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(episodes: List<Episode>)

    @Query("DELETE FROM episodes where id=:id")
    suspend fun deleteOne(id: Int)


}