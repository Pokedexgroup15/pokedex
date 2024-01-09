package com.example.pokedex.domain

import com.example.pokedex.data.GenderRate

data class Pokemon(
    val name: String,
    val pictureURL: String,
    val id: Int,
    val type1: String,
    val type2: String,
    val pokedexText: String,
    val capture_rate: Int,
    val growth_rate: String,
    val genderRate: GenderRate
 )