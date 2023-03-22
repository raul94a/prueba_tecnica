package raulalbin.prueba.tecnica.data.models

import com.google.gson.annotations.SerializedName

open class EpisodesResponse(
    @SerializedName("info")
    val info: Pagination,
    @SerializedName("results")
    val results: List<Episode>
)
