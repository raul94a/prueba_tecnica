package raulalbin.prueba.tecnica.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import raulalbin.prueba.tecnica.R

@Entity(tableName = "locations")
data class Location (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,
    var name: String,
    var type: String,
    var dimension: String,

    var url: String,
    var created: String
)
{
    @Ignore
    lateinit var residents: List<String>
    override fun toString(): String {
        return "Location(name='$name', url='$url')"
    }


    fun mapTypeWithDrawable() : Map<String, Int>{
        return mapOf(
            "Planet" to R.drawable.ic_planet,
            "Cluster" to R.drawable.ic_cluster,
            "Space station" to R.drawable.ic_space_station,
            "Microverse" to R.drawable._751251091659281605,
            "Resort" to R.drawable._05311321639208588,
            "TV" to R.drawable._937526791542284630
        )
    }
}
