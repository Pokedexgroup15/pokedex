package com.example.pokedex.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.pokedex.domain.Pokemon
import com.google.gson.Gson

import kotlinx.coroutines.flow.Flow




    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM Text")
        fun getAll(): Flow<List<LocalPokemon>>



        @Insert(onConflict = OnConflictStrategy.IGNORE)

        fun insert(localPokemon: LocalPokemon)

        @Delete
         fun delete(localPokemon: LocalPokemon)

        @Query("SELECT * FROM Text ORDER BY info ASC")
        fun getAllSortedAscending(): Flow<List<LocalPokemon>>

        @Query("SELECT * FROM Text ORDER BY info DESC")
        fun getAllSortedDescending(): Flow<List<LocalPokemon>>
    }

fun serializeToJson(pokemon: Pokemon): String {
    val gson = Gson()
    return gson.toJson(pokemon)
}

fun deserializeFromJson(jsonString: String): Pokemon {
    val gson = Gson()
    return gson.fromJson(jsonString, Pokemon::class.java)
}