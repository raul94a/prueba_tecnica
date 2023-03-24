package raulalbin.prueba.tecnica.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import raulalbin.prueba.tecnica.data.database.AppDatabase
import raulalbin.prueba.tecnica.data.models.*
import raulalbin.prueba.tecnica.data.repositories.RickAndMortyRepository
import javax.inject.Inject




@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val database: AppDatabase
) :
    ViewModel() {


    private var _characters = MutableStateFlow<CharactersResponse?>(null)
    val characters: StateFlow<CharactersResponse?>
        get() = _characters

    private var _episodes = MutableStateFlow<EpisodesResponse?>(null)
    val episodes: StateFlow<EpisodesResponse?>
        get() = _episodes

    private var _locations = MutableStateFlow<LocationsResponse?>(null)
    val locations: StateFlow<LocationsResponse?>
        get() = _locations

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private var _characterEpisodes = MutableStateFlow(listOf<Episode>())
    val characterEpisodes: StateFlow<List<Episode>>
        get() = _characterEpisodes

    fun getCharacters() {
        _loading.value = true
        viewModelScope.launch {

            val response = repository.getCharacters()
            database.characterDao().insertMany(response?.results ?: emptyList())
            if (response != null) {
                setFirstEpisodeFromList(response.results)
                _characters.value = response
            }
            _loading.value = false
        }

    }

    fun nextPage() {
        if (characters.value == null || characters.value!!.info.next == null) {

            return
        }
        _loading.value = true
        viewModelScope.launch {
            val response = repository.getCharactersFromPagination(characters.value!!.info.next!!)
            _characters.value = null
            if (response != null) {
                database.characterDao().insertMany(response.results)
                setFirstEpisodeFromList(response.results)
                _characters.value = response

            }
            _loading.value = false
        }

    }

    fun previousPage() {
        if (characters.value == null || characters.value!!.info.prev == null) {
            return
        }
        _loading.value = true
        viewModelScope.launch {
            val response = repository.getCharactersFromPagination(characters.value!!.info.prev!!)

            if (response != null) {
                database.characterDao().insertMany(response.results)
                setFirstEpisodeFromList(response.results)
                _characters.value = response

            }
            _loading.value = false
        }

    }

    fun getCharacter(id: String) {
        _loading.value = true
        viewModelScope.launch {
            val character = repository.getCharacterById(id)
            if (character != null) {
                val episodes = character.episode
                Log.e("Char episodes", "" + episodes)
                var episodesKeys = mutableListOf<String>()
                for (episode in episodes) {
                    val id = episode.substringAfterLast("/")
                    episodesKeys.add(id)
                }
                Log.e("EPISODES", "" + episodesKeys)
                val response = repository.searchEpisodesFromList(episodesKeys)
                if (response != null) {
                    database.episodeDao().insertMany(response)
                    // getCharactersFromEpisodes(response.results)
                    _characterEpisodes.value = response
                }
            }
            _loading.value = false
        }
    }
        fun searchCharacters(name: String) {
            _loading.value = true
            viewModelScope.launch {

                val response = repository.searchCharactersByName(name)
                database.characterDao().insertMany(response?.results ?: emptyList())
                if (response != null) {
                    setFirstEpisodeFromList(response.results)
                    _characters.value = response
                }
                _loading.value = false
            }
        }

        private suspend fun setFirstEpisodeFromList(tvCharacters: List<TvCharacter>) {
            for (item in tvCharacters) {
                val episodeDB = database.episodeDao().getEpisodeByUrl(item.episode[0])
                if (episodeDB != null) {
                    item.firstEpisode = episodeDB
                } else {
                    val episode = item.episode[0]
                    val firstEpisode = repository.getEpisode(episode)
                    item.firstEpisode = firstEpisode!!
                }
            }
        }

        fun getEpisodes() {
            _loading.value = true
            viewModelScope.launch {
                val response = repository.getEpisodes()
                if (response != null) {
                    database.episodeDao().insertMany(response.results)
                    getCharactersFromEpisodes(response.results)
                    _episodes.value = response
                }
                _loading.value = false
            }
        }

        fun previousPageEpisodes() {
            if (episodes.value == null || episodes.value!!.info.prev == null) {
                return
            }
            _loading.value = true
            try {

                viewModelScope.launch {
                    val response =
                        repository.getEpisodesFromPagination(episodes.value!!.info.prev!!)

                    if (response != null) {
                        database.episodeDao().insertMany(response.results)
                        getCharactersFromEpisodes(response.results)
                        _episodes.value = response

                    }
                    _loading.value = false
                }
            } catch (e: Exception) {
                Log.e("Error", "$e")
            }
        }

        fun nextPageEpisodes() {
            if (episodes.value == null || episodes.value!!.info.next == null) {

                return
            }
            _loading.value = true
            viewModelScope.launch {
                val response = repository.getEpisodesFromPagination(episodes.value!!.info.next!!)
                _episodes.value = null
                if (response != null) {
                    database.episodeDao().insertMany(response.results)
                    getCharactersFromEpisodes(response.results)
                    _episodes.value = response

                }
                _loading.value = false
            }

        }

        fun searchEpisodes(name: String) {
            _loading.value = true
            viewModelScope.launch {
                val response = repository.searchEpisodesByName(name)
                if (response != null) {
                    database.episodeDao().insertMany(response.results)
                    getCharactersFromEpisodes(response.results)
                    _episodes.value = response
                }
                _loading.value = false
            }
        }

        fun getEpisodesFromList(episodes: List<String>) {
            _loading.value = true
            viewModelScope.launch {
                val response = repository.searchEpisodesFromList(episodes)
                if (response != null) {
                    database.episodeDao().insertMany(response)
                    // getCharactersFromEpisodes(response.results)
                    _characterEpisodes.value = response
                }
                _loading.value = false
            }
        }

        private suspend fun getCharactersFromEpisodes(episodes: List<Episode>) {
            val maxCharactersToFetch = 5
            var counter: Int
            for (item in episodes) {
                item.charactersImages = mutableListOf()
                counter = 0

                for (char in item.characters) {
                    if (counter < maxCharactersToFetch) {



                        val id: Long =
                            char.substringAfterLast("/")
                                .toLong()

                        val characterDB: TvCharacter? = database.characterDao().getCharacter(id)
                        if (characterDB != null) {
                            item.charactersImages.add(requireNotNull(characterDB.image))
                        } else {
                            val character = repository.getCharacterFromFullURL(char)
                            try {

                                database.characterDao().insertOne(character!!)
                                item.charactersImages.add(requireNotNull(character.image))
                            } catch (exception: Exception) {
                                Log.e("GetEpisodes", "Error $exception")
                            }
                        }

                    } else {
                        continue
                    }
                    counter++
                }
            }

        }


        fun getLocations() {
            _loading.value = true
            viewModelScope.launch {
                val response = repository.getLocations()

                if (response != null) {
                    database.locationDao().insertMany(response.results)
                    _locations.value = response

                }
                _loading.value = false
            }
        }

        fun getLocationsNextPage() {

            if (locations.value == null || locations.value!!.info.next == null) {

                return
            }
            _loading.value = true
            viewModelScope.launch {
                val response = repository.getLocationsFromPagination(locations.value!!.info.next!!)
                _locations.value = null
                if (response != null) {
                    database.locationDao().insertMany(response.results)
                    _locations.value = response

                }
                _loading.value = false
            }


        }

        fun getLocationsPreviousPage() {

            if (locations.value == null || locations.value!!.info.prev == null) {

                return
            }
            _loading.value = true
            viewModelScope.launch {
                val response = repository.getLocationsFromPagination(locations.value!!.info.prev!!)
                _locations.value = null
                if (response != null) {
                    database.locationDao().insertMany(response.results)
                    _locations.value = response

                }
                _loading.value = false
            }


        }

        fun searchLocations(name: String) {
            _loading.value = true
            viewModelScope.launch {
                val response = repository.searchLocationsByName(name)

                if (response != null) {
                    database.locationDao().insertMany(response.results)
                    _locations.value = response

                }
                _loading.value = false
            }
        }

    }