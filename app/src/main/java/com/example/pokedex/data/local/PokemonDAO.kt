package com.example.pokedex.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow



    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM Favourite")
      suspend  fun getAll(): Flow<ArrayList<Pokemon>>


        @Insert
        fun insert(vararg users: Pokemon)

        @Delete
        fun delete(user: Pokemon)
    }




