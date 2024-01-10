package com.example.pokedex.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.data.RepositoryImpl
import kotlinx.coroutines.flow.StateFlow
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel : ViewModel() {

    private var selectedPokemon: Pokemon? = null

    var Pokemons = PokemonObject.pokeList

    var PokemonsFave = PokemonObject.faveList

    var PokemonsFilter = PokemonObject.filteredList

    var repository = RepositoryImpl()


    fun getData(isFavorite: Boolean, sortOption: SortOption? = null): StateFlow<ArrayList<Pokemon>> {
        var list = Pokemons
        if (isFavorite)
        { list =PokemonsFave}
        else if (PokemonObject.filter){
            list = PokemonsFilter }
Log.d("filterr",""+PokemonObject.filter)
        // Apply sorting if a sort option is provided
        return (sortOption?.let { sortPokemonList(list.value, it) } ?: list) as StateFlow<ArrayList<Pokemon>>
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
        if (PokemonsFave.value.contains(pokemon))
            PokemonsFave.value.remove(pokemon)
        else
            PokemonsFave.value.add(pokemon)
    }


}
