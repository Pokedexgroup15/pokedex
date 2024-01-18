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




class MainActivity : ComponentActivity() {
 private   val database by lazy {
     Room.databaseBuilder(
         applicationContext,
         PokemonDatabase::class.java, "favourite_database15"
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