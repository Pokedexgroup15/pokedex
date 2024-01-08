package com.example.pokedex.viweModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import java.net.URL
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
object RetrofitBase {

    val baseUrl = "https://pokeapi.co/api/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())                 .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
interface PokeApi{
    @GET("pokemon/{id}")

    suspend fun getPokemonInfo(@Path("id") pathId: Int) : Response<PokemonInfo>
}


data class PokemonInfo(
    val id: Int,
    val name: String
//    val types:String
)

//data class PokemonTypes(
// val 0: String,
// val 1: String
//)


class RepositoryImpl: ViewModel() {
//    load image
    //imageView.load("https://example.com/image.jpg")

  fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){


          val quotesApi = RetrofitBase.getInstance().create(PokeApi::class.java)


        viewModelScope.launch(Dispatchers.IO){
//            val fileName = "json/pokemonCache.json"
//            var file = File(fileName)
//            var cacheJson = JSONObject(file.readText())
            val limit = start+end
//            val jsonData =URL("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$start").readText()
//            val pokemonJson = JSONObject(jsonData)
            var i : Int = start
            GlobalScope.launch {
                while(i <= end) {
                val result = quotesApi.getPokemonInfo(i)
                result.body()?.let { Log.d("test", it.name)
//                PokemonObject.pokeList.add(Pokemon(it.name.replaceFirstChar { it.uppercase() }, pokeDefaultPictureFront, it.id,type1,type2, pokedexTextList))

                    i++
                }}
            }
//            while(i <= end){
//                // launching a new coroutine
//
//////            if(cleanCopy and (!cacheJson.has("$i"))) {
////
//                val jsonData2 = URL("https://pokeapi.co/api/v2/pokemon/$i").readText()
//                val jsonDataSpecies = URL("https://pokeapi.co/api/v2/pokemon-species/$i").readText()
//////            }
//                var pokemonJson2  = JSONObject(jsonData2)
//                var pokemonJsonSpecies = JSONObject(jsonDataSpecies)
//                if (pokemonJson2.getBoolean("is_default") or !onlyDefaults) {
//                    var pokeName: String =
//                        pokemonJson2.getString("name")
//                    var pokeId: Int =
//                        pokemonJson2.getInt("id")
//                    var pokeDefaultPictureFront: String =
//                        pokemonJson2.getJSONObject(
//                            "sprites"
//                        ).getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
//                    var type1: String =pokemonJson2.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
//
//
//
//                    var type2: String = "null"
//                    if (pokemonJson2.getJSONArray("types").length()>1) {
//                        type2 = pokemonJson2.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name")
//                    }
//                    var pokedexTextList = ArrayList<String>()
//                    pokedexTextList.add(pokemonJsonSpecies.getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text"))
//
//                        Log.d("info",""+pokeName+" "+pokeDefaultPictureFront+" "+ pokeId+" "+  type1+" "+type2)
//
//                    PokemonObject.pokeList.add(Pokemon(pokeName.replaceFirstChar { it.uppercase() }, pokeDefaultPictureFront, pokeId,type1,type2, pokedexTextList))
////                cacheJson.put(""+pokeId,pokeName)
//                }
//
//                i++
//        }

        }

    }}



