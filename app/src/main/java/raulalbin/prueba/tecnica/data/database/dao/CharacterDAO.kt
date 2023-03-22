package raulalbin.prueba.tecnica.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import raulalbin.prueba.tecnica.data.models.TvCharacter



@Dao
interface CharacterDAO {
    @Query("Select * from characters ORDER BY id")
    suspend fun  getCharacters():List<TvCharacter>

    @Query("Select * from characters where id = :id")
    suspend fun getCharacter(id: Long) : TvCharacter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(contact: TvCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(contacts: List<TvCharacter>)

    @Query("DELETE FROM characters where id=:id")
    suspend fun deleteOne(id: Long)


}