package raulalbin.prueba.tecnica.data.models

import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("info")
    val info: Pagination,
    @SerializedName("results")
    val results: List<Location>
) {
    override fun toString(): String {
        return "LocationsResponse(info=$info, results=$results)"
    }
}
