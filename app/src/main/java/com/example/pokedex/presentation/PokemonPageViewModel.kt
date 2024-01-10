package com.example.pokedex.presentation

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.data.RepositoryImpl
import kotlinx.coroutines.flow.StateFlow

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel : ViewModel() {

    private var selectedPokemon: Pokemon? = null

    var Pokemons = PokemonObject._pokeList

    var PokemonsFave = PokemonObject.faveList

    var repository = RepositoryImpl()


    fun getData(isFavorite: Boolean, sortOption: SortOption? = null): StateFlow<ArrayList<Pokemon>> {
        val list = if (isFavorite) PokemonsFave else Pokemons

        // Apply sorting if a sort option is provided
        return list as StateFlow<ArrayList<Pokemon>>
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
