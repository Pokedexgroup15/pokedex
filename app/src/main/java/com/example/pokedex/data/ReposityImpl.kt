package com.example.pokedex.data

import com.example.pokedex.domain.Pokemon
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.data.api.PokemonApiService
import com.example.pokedex.PokemonObject

class PokemonRepositoryImpl(private val apiService: PokemonApiService) : PokemonRepository {
    var Pokemons = PokemonObject._pokeList

    override suspend fun getPokemonList(): List<Pokemon> {
        // Make API call using apiService
        // Parse the response and return the list of Pokemon
        return Pokemons.value
    }
}