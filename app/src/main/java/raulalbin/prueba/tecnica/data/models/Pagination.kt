package raulalbin.prueba.tecnica.data.models

import com.google.gson.annotations.SerializedName

data class Pagination(
    val count: Long,
    val pages: Long,
    val next: String? = null,
    val prev: String? = null
) {
    override fun toString(): String {
        return "Pagination(count=$count, pages=$pages, next='$next', prev=$prev)"
    }
}
