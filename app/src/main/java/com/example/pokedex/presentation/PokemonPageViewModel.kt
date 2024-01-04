package com.example.pokedex.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.viweModel.RepositoryImpl

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel : ViewModel() {

    private var selectedPokemon: Pokemon? = null
    var PokemonsFave = PokemonObject.faveList

    var Pokemons = PokemonObject.pokeList

    var repository = RepositoryImpl()


    fun getMockData(isFavorite: Boolean, sortOption: SortOption? = null): List<Pokemon> {

        val list = if (isFavorite) PokemonsFave else Pokemons

        // Apply sorting if a sort option is provided
        return sortOption?.let { sortPokemonList(list, it) } ?: list
    }
    private fun sortPokemonList(pokemonList: List<Pokemon>, sortOption: SortOption): List<Pokemon> {
        return when (sortOption) {
            SortOption.LowToHigh -> pokemonList.sortedBy { it.id }
            SortOption.HighToLow -> pokemonList.sortedByDescending { it.id }
        }
    }




    fun getPokemon(): Pokemon?{
        return selectedPokemon
    }
    fun setPokemon(pokemon: Pokemon){
      selectedPokemon = pokemon
    }


    fun toggleFavourite(pokemon: Pokemon){
        if (PokemonsFave.contains(pokemon))
            PokemonsFave.remove(pokemon)
        else
            PokemonsFave.add(pokemon)
    }


}
