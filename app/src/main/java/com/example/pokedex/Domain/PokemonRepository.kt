package com.example.pokedex.Domain


interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>

}