package com.example.pokedex.presentation

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.data.RepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel : ViewModel() {

    private var selectedPokemon: Pokemon? = null
    var PokemonsFave = PokemonObject.faveList

    var Pokemons = PokemonObject.pokeList

    var repository = RepositoryImpl()


    fun getMockData(isFavorite: Boolean, sortOption: SortOption? = null): StateFlow<ArrayList<Pokemon>> {

        val list = if (isFavorite) PokemonsFave else Pokemons

        // Apply sorting if a sort option is provided
      // sortOption?.let { sortPokemonList(list, it) } ?: list
        return Pokemons
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
