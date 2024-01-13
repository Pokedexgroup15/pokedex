package com.example.pokedex.domain


//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.example.pokedex.data.repository.GenderRate


//@Entity
data class Pokemon(
    val name: String,
    val pictureURL: String,
    //@PrimaryKey(autoGenerate = true)
    val id: Int,
    val type1: String,
    val type2: String,
    val pokedexText: String,
    val capture_rate: Int,
    val growth_rate: String,
    val genderRate: GenderRate,
    val hp:Int,
    val attack:Int,
    val defense:Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int,
    val generation: Int,
    val abilities: ArrayList<String>
 )
