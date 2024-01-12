package com.example.pokedex.presentation.userInterface.whosthat
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokeApi
import com.example.pokedex.data.PokemonInfo
import kotlinx.coroutines.launch


class WhosThatPokemonViewModel (
    private val pokeApi: PokeApi
) :ViewModel(){
    private val _pokemon = mutableStateOf<PokemonInfo?>(null)
    val pokemon: State<PokemonInfo?> = _pokemon
    var guessAttempt by mutableStateOf("")
    var isGuessCorrect by mutableStateOf(false)

    fun fetchRandomPokemon(){
        viewModelScope.launch {
            val randomId = (1..898).random()
            val response = pokeApi.getPokemonInfo(randomId)
            if(response.isSuccessful){
                _pokemon.value = response.body()
                isGuessCorrect = false
            } else{
            }
        }
    }

fun checkGuess(){
    isGuessCorrect = guessAttempt.equals(pokemon.value?.name, ignoreCase = true)
}
    fun resetGame(){
        fetchRandomPokemon()
        guessAttempt = ""
        isGuessCorrect = false
    }

    init{
        fetchRandomPokemon()
    }
}