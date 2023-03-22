package raulalbin.prueba.tecnica.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import raulalbin.prueba.tecnica.data.repositories.CharacterRepository
import raulalbin.prueba.tecnica.data.models.CharactersResponse
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {
    private var _characters = MutableStateFlow<CharactersResponse?>(null)
    val characters: StateFlow<CharactersResponse?>
        get() = _characters

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean>
        get() = _loading

     fun getCharacters() {
         _loading.value = true
        viewModelScope.launch {
            val response = repository.getCharacters()

            if (response != null) {
                _characters.value = response

            }
            _loading.value = false
        }

    }

    fun nextPage() {
        if(characters.value == null || characters.value!!.info.next == null){
            return
        }
        viewModelScope.launch {
            val response = repository.getCharactersFromPagination(characters.value!!.info.next!!)
            _characters.value = null;
            if (response != null) {
                _characters.value = response

            }
            _loading.value = false
        }

    }

    fun previousPage() {
        if(characters.value == null || characters.value!!.info.prev == null){
            return
        }
        viewModelScope.launch {
            val response = repository.getCharactersFromPagination(characters.value!!.info.prev!!)
            Log.i("RESPONSE", "" + response)
            if (response != null) {
                _characters.value = response

            }
            _loading.value = false
        }

    }

}