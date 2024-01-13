package com.example.pokedex.domain


interface PokemonRepository {
     suspend fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){}
}