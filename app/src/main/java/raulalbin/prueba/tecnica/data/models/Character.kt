package raulalbin.prueba.tecnica.data.models


data class Character (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
){
    override fun toString(): String {
        return "Character(id=$id, name='$name', status='$status', species='$species', type='$type', gender='$gender', origin=$origin, location=$location, image='$image', episode=$episode, url='$url', created='$created')"
    }
}