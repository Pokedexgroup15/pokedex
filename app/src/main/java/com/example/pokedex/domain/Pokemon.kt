package com.example.pokedex.domain

import com.example.pokedex.data.Abilities
import com.example.pokedex.data.Generation

data class Pokemon(
    val name: String,
    val pictureURL: String,
    val id: Int,
    val type1: String,
    val type2: String,
    val pokedexText: String,
    val capture_rate: Int,
    val growth_rate: String,
    val hp:Int,
    val attack:Int,
    val defense:Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int,
    val generation: Int,
    val abilities: ArrayList<String>
 )