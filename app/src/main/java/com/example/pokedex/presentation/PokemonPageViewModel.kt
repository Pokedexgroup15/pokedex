package com.example.pokedex.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.data.repository.RepositoryImpl
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import kotlinx.coroutines.flow.StateFlow
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel : ViewModel() {

    private var selectedPokemon: Pokemon? = null

    var Pokemons = PokemonObject.pokeList

    var PokemonsFave = PokemonObject.faveList

    var PokemonsFilter = PokemonObject.filteredList



    fun getData(isFavorite: Boolean, sortOption: SortOption? = null): StateFlow<ArrayList<Pokemon>> {
        var list = Pokemons
        if (isFavorite)
        { list =PokemonsFave}
        else if (PokemonObject.filter){
            list = PokemonsFilter }
Log.d("filterr",""+PokemonObject.filter)
        // Apply sorting if a sort option is provided
        return list as StateFlow<ArrayList<Pokemon>>
    }

    fun getALLPokemons(){
        viewModelScope.launch(Dispatchers.IO){
            RepositoryImpl().addPokemon(1,1000,true,true)

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
