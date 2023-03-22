package raulalbin.prueba.tecnica.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import raulalbin.prueba.tecnica.data.api.RickAndMortyAPI
import raulalbin.prueba.tecnica.data.models.CharactersResponse
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val api: RickAndMortyAPI){

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
}