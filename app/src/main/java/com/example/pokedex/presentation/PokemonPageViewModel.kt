package com.example.pokedex.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.data.RepositoryImpl
import com.example.pokedex.data.local.PokemonDAO
import com.example.pokedex.data.local.PokemonDatabase
import kotlinx.coroutines.flow.StateFlow
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel(
    private val dao: PokemonDAO
) : ViewModel() {

    private var selectedPokemon: Pokemon? = null

    var Pokemons = PokemonObject.pokeList

    var PokemonsFave = PokemonObject.faveList

    var PokemonsFilter = PokemonObject.filteredList

    var repository = RepositoryImpl()
   var Dao = dao

    fun getData(isFavorite: Boolean, sortOption: SortOption? = null): StateFlow<ArrayList<Pokemon>> {
        var list = Pokemons
        if (isFavorite)
        {



            list =PokemonsFave



        }
        else if (PokemonObject.filter){
            list = PokemonsFilter }
Log.d("filterr",""+PokemonObject.filter)
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
        if (PokemonsFave.value.contains(pokemon)) {
            PokemonsFave.value.remove(pokemon)

          /*  viewModelScope.launch(Dispatchers.IO) {
                database?.dao?.insert(pokemon)
            }

           */
        }
        else {
            PokemonsFave.value.add(pokemon)

            /*viewModelScope.launch(Dispatchers.IO) {
                database?.dao?.delete(pokemon)
            }

             */
        }
    }


}
