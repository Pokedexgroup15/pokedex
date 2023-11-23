package com.example.pokedex.UserInterface.favoritePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.Pokemon
import com.example.pokedex.data.FavoritesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class FavoritesViewModel (private val favoritesDataSource: FavoritesDataSource) : ViewModel() {
    val favorites: Flow<List<Pokemon>> = favoritesDataSource.getFavorites()

    fun onFavoriteButtonClicked(pokemon: Pokemon){
        viewModelScope.launch(Dispatchers.IO){
            favoritesDataSource.toggleFavorite(pokemon)
        }
    }


}