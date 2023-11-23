package com.example.pokedex.data

import com.example.pokedex.Pokemon
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSource {
    fun getFavorites(): Flow<List<Pokemon>>
    suspend fun toggleFavorite(pokemon: Pokemon)
}