package raulalbin.prueba.tecnica.data.models

data class Location (
    val name: String,
    val url: String
){
    override fun toString(): String {
        return "Location(name='$name', url='$url')"
    }
}
