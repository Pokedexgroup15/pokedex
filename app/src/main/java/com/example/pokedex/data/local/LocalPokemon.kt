package com.example.pokedex.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Text")
data class LocalPokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val info:String,
    val type1:String,
    val type2:String,
    val Generation: Int,
    val capture:Int,
    val growth:String


)
