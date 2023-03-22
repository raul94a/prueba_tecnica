package raulalbin.prueba.tecnica.data.models

import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("info")
    val info: Pagination,
    @SerializedName("results")
    val results: List<Character>
){
    override fun toString(): String {
        return "CharactersResponse(info=$info, results=$results)"
    }
}