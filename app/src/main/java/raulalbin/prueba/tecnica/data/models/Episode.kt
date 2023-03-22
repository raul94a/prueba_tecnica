package raulalbin.prueba.tecnica.data.models



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
open class Episode (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,
    var name: String,
    @SerializedName("air_date")
    var airDate: String?,
    var episode: String,

    var url: String,
    var created: String,

) {
    @Ignore
     var charactersImages: MutableList<String>  = arrayListOf()
    @Ignore
    lateinit var characters: List<String>
    override fun toString(): String {
        return "Episode(id=$id, name='$name', airDate='$airDate', episode='$episode', characters=$characters, url='$url', created='$created')"
    }
}
