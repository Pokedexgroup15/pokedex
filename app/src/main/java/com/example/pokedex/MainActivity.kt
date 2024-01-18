package com.example.pokedex
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.pokedex.data.RepositoryImpl
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.theme.PokedexTheme
import com.example.pokedex.presentation.navigation.navStart
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.domain.PokemonForm
import com.example.pokedex.presentation.searchPageViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
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
    var abilMap =HashMap<String, String>()
    var formMap =HashMap<String, PokemonForm>()
    var varianceMap =HashMap<String, PokemonForm>()
    var tempEnd = 20


}
class MainActivity : ComponentActivity() {
 private   val database by lazy {
     Room.databaseBuilder(
         applicationContext,
         PokemonDatabase::class.java, "favourite_database17"
     ).fallbackToDestructiveMigration()
         .build()

 }

private val viewmodel by viewModels<searchPageViewModel> (
factoryProducer =  {
    object : ViewModelProvider.Factory{
       override  fun <T : ViewModel> create(modelClass: Class<T>): T {
           return searchPageViewModel(database.dao) as T        }
    }
}
)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RepositoryImpl(database.dao).addPokemon(1,1025,true,true)
viewmodel.initialize()

        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    navStart(viewmodel)
                }
            }
        }


    }
}
fun deserializeFromJson(jsonString: String): Pokemon {
    val gson = Gson()
    return gson.fromJson(jsonString, Pokemon::class.java)
}