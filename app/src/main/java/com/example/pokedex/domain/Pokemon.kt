package com.example.pokedex.domain


import com.example.pokedex.Gender



data class Pokemon(
    val name: String,
    val pictureURL: String,
    val id: Int,
    val type1: String,
    val type2: String,
    val pokedexText: String,
    val capture_rate: Int,
    val growth_rate: String,
    var genderRate: GenderRate ,
    val hp:Int,
    val attack:Int,
    val defense:Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int,
    val generation: Int,
    val abilities: List<String>
)

data class GenderRate(
    val id: Int,
    val gender: Gender,
    val maleRatio: Double,
    val femaleRatio: Double,

)



/*
@Entity("Pokemons")
data class Favourite(
    @PrimaryKey(autoGenerate = true)

    val name: String
)

 */