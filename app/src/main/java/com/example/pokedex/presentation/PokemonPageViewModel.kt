package com.example.pokedex.presentation

import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.SortOption
import com.example.pokedex.data.RepositoryImpl
import com.example.pokedex.data.local.LocalPokemon
import com.example.pokedex.data.local.PokemonDAO
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.deserializeFromJson
import kotlinx.coroutines.flow.StateFlow
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

class searchPageViewModel(
    private val dao: PokemonDAO
) : ViewModel() {

    private var selectedPokemon: Pokemon? = null

    var Pokemons = PokemonObject.pokeList

    var PokemonsFave = PokemonObject.faveList

    var PokemonsFilter = PokemonObject.filteredList
    lateinit var flow: Flow<List<LocalPokemon>>




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


    fun toggleFavourite(pokemon: Pokemon,Favorized:Boolean) {
        viewModelScope.launch {

                withContext(Dispatchers.IO) {
                    if (Favorized) {
                        dao.insert(
                            LocalPokemon(
                                pokemon.id,
                                serializeToJson(pokemon),
                                pokemon.type1,
                                pokemon.type2,
                                pokemon.generation,
                                pokemon.capture_rate,
                                pokemon.growth_rate
                            )
                        )
                    } else {
                        dao.delete(
                            LocalPokemon(
                                pokemon.id,
                                serializeToJson(pokemon),
                                pokemon.type1,
                                pokemon.type2,
                                pokemon.generation,
                                pokemon.capture_rate,
                                pokemon.growth_rate
                            )
                        )
                    }

            }
        }

    }
    fun initialize() {
        viewModelScope.launch {
            flow = dao.getAll()

            flow.collect { localPokemonList ->
                val arrayList = ArrayList(localPokemonList)

                val data = arrayList

                val updateList = ArrayList<Pokemon>()

                for (gen in data.indices) {
                    val pk = deserializeFromJson(data[gen].info)
                    updateList.add(pk)
                }


                    PokemonObject._faveList.value = updateList


            }
        }}}

    fun serializeToJson(pokemon: Pokemon): String {
        val gson = Gson()
        return gson.toJson(pokemon)
    }



