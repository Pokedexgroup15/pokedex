package com.example.pokedex.data.remote

import com.example.pokedex.data.repository.PokemonEve
import com.example.pokedex.data.repository.PokemonInfo
import com.example.pokedex.data.repository.PokemonSpecies
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Path



    interface PokemonAPI{
        @GET("pokemon/{id}")

        suspend fun getPokemonInfo(@Path("id") pathId: Int) : retrofit2.Response<PokemonInfo>


        @GET("pokemon-species/{id}")

        suspend fun getPokemonSpeciesInfo(@Path("id") pathId: Int) : retrofit2.Response<PokemonSpecies>



        @GET("https://pokeapi.co/api/v2/evolution-chain/{id}")

        suspend fun getPokemonEveInfo(@Path("id") pathId: Int) : retrofit2.Response<PokemonEve>
    }


