package com.example.pokedex.presentation.userInterface.filterPage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.Pokemon



// ViewModel
class FilterViewModel : ViewModel() {
    val _selectedSortOption = mutableStateOf<SortOption?>(null)
    val selectedSortOption: State<SortOption?> = _selectedSortOption

    var pokemonList: List<Pokemon> = listOf()

    /*fun sortPokemonList(sortOption: SortOption): List<Pokemon> {
        return when (sortOption) {
            SortOption.LowToHigh -> pokemonList.sortedBy { it.id }
            SortOption.HighToLow -> pokemonList.sortedByDescending { it.id }
        }
    }




    fun setSortOption(sortOption: SortOption?) {
        _selectedSortOption.value = sortOption
       pokemonList = sortOption?.let { sortPokemonList(it) } ?: pokemonList
       // pokemonList = getSortedPokemonList()
    }

    fun getSortedPokemonList(): List<Pokemon> {
        return when (_selectedSortOption.value) {
            SortOption.LowToHigh -> pokemonList.sortedBy { it.id }
            SortOption.HighToLow -> pokemonList.sortedByDescending { it.id }
            null -> pokemonList
        }
    }

     */
}