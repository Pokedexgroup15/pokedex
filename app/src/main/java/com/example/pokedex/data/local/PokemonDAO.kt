package com.example.pokedex.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase

import kotlinx.coroutines.flow.Flow




    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM Text")
        fun getAll(): Flow<List<LocalPokemon>>
    }
