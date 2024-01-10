package com.example.pokedex.presentation.userInterface.filterPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokemonObject
import com.example.pokedex.domain.FilterPokemon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait

//import com.example.pokedex.Presentation.UserInterface.FilterPage.SortOption


class ResetViewModel : ViewModel() {
    val selectedSortOption = mutableStateOf<SortOption?>(null)
    val selectedTypes = mutableStateOf<List<Int>>(emptyList())
    val selectedGeneration = mutableStateOf(-1)
    val selectedName = mutableStateOf<String?>(null)
    val selectedCatchRate = mutableStateOf<List<String>>(emptyList())
    var Pokemons = PokemonObject.pokeList.value
    init {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            PokemonObject.pokeList.collect { newpoke ->
                delay(1000//todo adjust sleep time
                if(PokemonObject.filter){
                runFilter()       }     }
        }
    }

    fun resetFilters() {
        selectedSortOption.value = null
        selectedTypes.value = emptyList()
        selectedCatchRate.value = emptyList()
        selectedGeneration.value = -1
        selectedName.value = null
    }
    fun runFilter(){
        FilterPokemon().filterList(
            selectedTypes,
            selectedCatchRate,
            selectedGeneration
        )
    }

}