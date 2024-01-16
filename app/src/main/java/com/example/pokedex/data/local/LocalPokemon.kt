package com.example.pokedex.data.local
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity("Text")
data class LocalPokemon(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var info :String="d"


){
    @Ignore
    constructor() : this(0, "d")
}
