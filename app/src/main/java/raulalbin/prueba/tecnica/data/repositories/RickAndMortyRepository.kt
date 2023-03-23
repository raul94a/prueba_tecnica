package raulalbin.prueba.tecnica.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import raulalbin.prueba.tecnica.data.api.RickAndMortyAPI

import raulalbin.prueba.tecnica.data.models.CharactersResponse
import raulalbin.prueba.tecnica.data.models.Episode
import raulalbin.prueba.tecnica.data.models.EpisodesResponse
import raulalbin.prueba.tecnica.data.models.LocationsResponse
import raulalbin.prueba.tecnica.data.models.TvCharacter
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(private val api: RickAndMortyAPI){

    suspend fun getCharacters(): CharactersResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getCharacters()

            response.body()
        }
    }

    suspend fun getCharactersFromPagination(url: String): CharactersResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getCharactersPagination(url)
            response.body()
        }
    }

    suspend fun getCharacterFromFullURL(url: String): TvCharacter? {
        return withContext(Dispatchers.IO){
            val response = api.getCharacterFromFullURL(url)
            response.body()
        }
    }

    suspend fun searchCharactersByName(name: String): CharactersResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getCharactersByName(name)
            response.body()
        }
    }


    suspend fun getEpisode(url: String) : Episode? {
        return withContext(Dispatchers.IO) {
            val response = api.getEpisode(url)
            response.body()
        }
    }

    suspend fun getEpisodes() : EpisodesResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.getEpisodes()
            response.body()
        }
    }
    suspend fun getEpisodesFromPagination(url: String): EpisodesResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getEpisodesPagination(url)
            response.body()
        }
    }

    suspend fun searchEpisodesByName(name: String): EpisodesResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getEpisodesByName(name)
            response.body()
        }
    }

    suspend fun getLocations() : LocationsResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.getLocations()
            response.body()
        }
    }
    suspend fun getLocationsFromPagination(url: String): LocationsResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getLocationsPagination(url)
            response.body()
        }
    }
    suspend fun searchLocationsByName(name: String): LocationsResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getLocationsByName(name)
            response.body()
        }
    }
}