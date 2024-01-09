package com.example.pokedex.data.api

import com.example.pokedex.domain.Pokemon
import retrofit2.http.GET

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(): List<Pokemon>
}