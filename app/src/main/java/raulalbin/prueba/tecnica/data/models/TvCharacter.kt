package raulalbin.prueba.tecnica.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
open class TvCharacter (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "species")
    var species: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "gender")
    var gender: String,

    var url: String,
    var created: String,
    var image: String
){
    @Ignore
    lateinit var episode: MutableList<String>
    @Ignore
    var firstEpisode: Episode? = null

    @Ignore
    var episodes: MutableList<Episode> = arrayListOf()

    @Ignore
    lateinit var origin: Location
    @Ignore
    lateinit var location: Location


    override fun toString(): String {
//        return "Character(id=$id, name='$name', status='$status', species='$species', type='$type', gender='$gender',  image='$image', episode=$episode, url='$url', created='$created')"
  return ""
    }
}