package com.example.pokedex.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.domain.PokemonandGender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow




    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM Trial")
        fun getAll(): Flow<List<PokemonandGender>>
    }
/*
        @Query("SELECT * FROM Trial WHERE name = :name")
        fun getPokemon(vararg name: String): Flow<PokemonandGender>

        @Query("SELECT * FROM Trial ORDER BY ID DESC")
        suspend fun getSortedDescending():Flow<List<PokemonandGender>>
        @Query("SELECT * FROM Trial ORDER BY ID ")
        suspend fun getSortedAscending():Flow<List<PokemonandGender>>



        /*
        @Query("SELECT * FROM Pokemons WHERE type1 = :type1 ")
        suspend fun getFilterType(vararg type1: String): ArrayList<Pokemon>



 */
*/





/*
@Dao
interface FavouriteDAO {
    @Query("SELECT * FROM Favourite")
    suspend  fun getAll(): Flow<ArrayList<Pokemon>>


    @Insert
    fun insert(vararg users: Pokemon)

    @Delete
    fun delete(user: Pokemon)
}



 */



