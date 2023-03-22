package raulalbin.prueba.tecnica.data.api

import raulalbin.prueba.tecnica.data.models.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RickAndMortyAPI {
    @GET("character")
    suspend fun getCharacters() : Response<CharactersResponse>

    @GET
    suspend fun getCharactersPagination(@Url url: String) : Response<CharactersResponse>

    @GET("character/{character}")
    suspend fun getCharacter(@Path("character") character : String = "1") : Response<Character>

}