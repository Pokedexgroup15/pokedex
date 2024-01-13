package com.example.pokedex
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.theme.PokedexTheme
import com.example.pokedex.presentation.navigation.navStart
import com.example.pokedex.data.RepositoryImpl
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.presentation.DependencyProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



object PokemonObject{
    var _pokeList = MutableStateFlow(ArrayList<Pokemon>())
    var pokeList = _pokeList.asStateFlow()
    var count = 0

    var filter:Boolean = false
    var _faveList = MutableStateFlow(ArrayList<Pokemon>())
    var faveList = _faveList.asStateFlow()
    var eveList = Array(549) {Array(3) {ArrayList<String>()} }
    var _filteredList = MutableStateFlow(ArrayList<Pokemon>())
    var filteredList = _filteredList.asStateFlow()
     var database: PokemonDatabase?= null

    fun getdatbasee(): PokemonDatabase? {
        return database
    }
    fun setDatabasee(Database: PokemonDatabase){
        database=Database
    }


}
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DependencyProvider.initialize(this)
        /*val database = Room.databaseBuilder(
            DependencyProvider.applicationContext,
            PokemonDatabase::class.java, "pokemon_database"
        ).build()
        PokemonObject.setDatabasee(database)


         */
        val database: PokemonDatabase? = null
        RepositoryImpl().addPokemon(1,1000,true,true)
        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    navStart(database)
                }
            }
        }


    }
}
