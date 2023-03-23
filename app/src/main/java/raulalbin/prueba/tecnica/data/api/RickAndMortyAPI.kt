package raulalbin.prueba.tecnica.data.api

import raulalbin.prueba.tecnica.data.models.CharactersResponse
import raulalbin.prueba.tecnica.data.models.Episode
import raulalbin.prueba.tecnica.data.models.EpisodesResponse
import raulalbin.prueba.tecnica.data.models.LocationsResponse
import raulalbin.prueba.tecnica.data.models.TvCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyAPI {
    //Characters EP
    @GET("character")
    suspend fun getCharacters(): Response<CharactersResponse>

    @GET("character/")
    suspend fun getCharactersByName(@Query("name") name: String) : Response<CharactersResponse>

    @GET
    suspend fun getCharactersPagination(@Url url: String): Response<CharactersResponse>

    @GET("character/{character}")
    suspend fun getCharacter(@Path("character") character: String = "1"): Response<TvCharacter>

    @GET()
    suspend fun getCharacterFromFullURL(@Url url: String): Response<TvCharacter>

    //Episodes EPs
    @GET("episode")
    suspend fun getEpisodes(): Response<EpisodesResponse>

    @GET
    suspend fun getEpisode(@Url url: String): Response<Episode>

    @GET("episode/")
    suspend fun getEpisodesByName(@Query("name") name: String): Response<EpisodesResponse>

    @GET
    suspend fun getEpisodesPagination(@Url url: String): Response<EpisodesResponse>

    //Locations EPs
    @GET("location")
    suspend fun getLocations(): Response<LocationsResponse>

    @GET
    suspend fun getLocationsPagination(@Url url: String): Response<LocationsResponse>

    @GET("location/")
    suspend fun getLocationsByName(@Query("name") name: String) : Response<LocationsResponse>
}