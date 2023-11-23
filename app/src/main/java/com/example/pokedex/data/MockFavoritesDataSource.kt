package com.example.pokedex.data

import com.example.pokedex.Pokemon
import com.example.pokedex.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockFavoritesDataSource : FavoritesDataSource {

    private val favoritesFlow= MutableStateFlow(
        listOf(
            Pokemon("", R.drawable.img_1),
            Pokemon("", R.drawable.img_2)

       )
    )

    override fun getFavorites(): Flow<List<Pokemon>> = favoritesFlow.asStateFlow()

    override suspend fun toggleFavorite(pokemon: Pokemon){
        val favorites = favoritesFlow.value
        val isFavorite= favorites.any{it.name==pokemon.name}
        favoritesFlow.value= if (isFavorite){
            favorites - pokemon
        } else {
            favorites + pokemon
        }
    }
}