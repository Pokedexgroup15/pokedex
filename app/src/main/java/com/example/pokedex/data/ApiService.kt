package com.example.pokedex.viweModel

import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.PokemonObject
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitBase {

    val baseUrl = "https://pokeapi.co/api/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
interface PokeApi{
    @GET("pokemon/{id}")

    suspend fun getPokemonInfo(@Path("id") pathId: Int) : Response<PokemonInfo>
}
interface PokeApiSpecies{
    @GET("pokemon-species/{id}")

    suspend fun getPokemonSpeciesInfo(@Path("id") pathId: Int) : Response<PokemonSpecies>
}


data class PokemonSpecies(
    val flavor_text_entries: List<flavor_texts>
)

data class flavor_texts(
val flavor_text: String
)


data class PokemonInfo(
    val id: Int,
    val name: String,
    val types: List<subType>,
    val sprites: sprite
)

data class sprite(
    val other: Other
)
data class Other(
    @SerializedName("official-artwork")
    val text :Offical
)
data class Offical(
    @SerializedName("front_default")
    val frontdefault: String
    )





data class subType(
    val slot: Int,
    val type:Type

)
data class Type(
    val name: String,
    val url:String
)






class RepositoryImpl: ViewModel() {
//    load image
    //imageView.load("https://example.com/image.jpg")

  fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){


          val quotesApi = RetrofitBase.getInstance().create(PokeApi::class.java)
      val speciesApi = RetrofitBase.getInstance().create(PokeApiSpecies::class.java)


        viewModelScope.launch(Dispatchers.IO){
//            val fileName = "json/pokemonCache.json"
//            var file = File(fileName)
//            var cacheJson = JSONObject(file.readText())
//            val jsonData =URL("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$start").readText()
//            val pokemonJson = JSONObject(jsonData)
            var i : Int = start
            GlobalScope.launch {
                while(i <= end) {
                val result = quotesApi.getPokemonInfo(i)
                val result2 = speciesApi.getPokemonSpeciesInfo(i)
                    result2.body()?.let { Log.d("test5", it.flavor_text_entries[0].flavor_text)
                    var pokedexEntry:String
                    pokedexEntry = it.flavor_text_entries[0].flavor_text

                result.body()?.let { Log.d("test5", it.types[0].type.name+" "+it.name)
                    var type2: String
                    if(it.types.size>1){
                         type2 = it.types[1].type.name
                    }
                    else  type2 = "null"
                    PokemonObject.pokeList.add(Pokemon(it.name.replaceFirstChar { it.uppercase() }, it.sprites.other.text.frontdefault, it.id,it.types[0].type.name,type2, pokedexEntry))
}}
                    i++
                }


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



