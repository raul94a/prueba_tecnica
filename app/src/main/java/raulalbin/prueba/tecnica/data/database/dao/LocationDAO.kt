package raulalbin.prueba.tecnica.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import raulalbin.prueba.tecnica.data.models.Location


@Dao
interface LocationDAO {
    @Query("Select * from locations ORDER BY id")
    fun getLoCATIONS(): Flow<List<Location>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(locations: List<Location>)

    @Query("DELETE FROM locations where id=:id")
    suspend fun deleteOne(id: Long)


}