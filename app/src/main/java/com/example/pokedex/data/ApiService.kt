package com.example.pokedex.data

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
import kotlin.math.log
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel

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

interface PokeEveChain{
    @GET("https://pokeapi.co/api/v2/evolution-chain/{id}")

    suspend fun getPokemonEveInfo(@Path("id") pathId: Int) : Response<PokemonEve>
}

data class PokemonEve(
    val chain: Chain

)

data class Chain(
    val is_baby: Boolean,
    val species: Species,
    val evolves_to: List<Evol>
)
data class Evol(
    val species: Species,
    val evolves_to: List<Evol>
)

data class Species(
    val name: String
)

data class PokemonSpecies(
    val flavor_text_entries: List<flavor_texts>,
    val capture_rate: Int,
    val growth_rate: Growth,
    val generation: Generation
)
data class Generation(
    val name: String
)

data class Growth(
    val name: String
)

data class flavor_texts(
    val flavor_text: String,
    val language: Language
)

data class Language(
    val name: String
)


data class PokemonInfo(
    val id: Int,
    val name: String,
    val types: List<subType>,
    val sprites: sprite,
    val stats: List<Stats>
)

data class Stats(
    val base_stat: Int
)

data class sprite(
    val other: Other
)
data class Other(
    @SerializedName("official-artwork")
    val text : Offical
)
data class Offical(
    @SerializedName("front_default")
    val frontdefault: String
    )





data class subType(
    val slot: Int,
    val type: Type

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
    val eveApi = RetrofitBase.getInstance().create(PokeEveChain::class.java)

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

                if(i<549){
                    val result3 = eveApi.getPokemonEveInfo(i)
                    result3.body()?.let {

                        PokemonObject.eveList[i][0].add(it.chain.species.name)

                        var i2: Int =0
                        var i3: Int =0


                        while( i2<it.chain.evolves_to.size){
                            PokemonObject.eveList[i][1].add(it.chain.evolves_to[i2].species.name)
                            i3 =0
                            while( i3<it.chain.evolves_to[i2].evolves_to.size) {

                                PokemonObject.eveList[i][2].add(it.chain.evolves_to[i2].evolves_to[i3].species.name)
                                i3++
                            }
                            i2++
                        }
                        Log.d("eve",PokemonObject.eveList[i][0].toString() +" -> "+PokemonObject.eveList[i][1].toString()+" -> "+ PokemonObject.eveList[i][2])
                        PokemonObject.eveList





                    }


                }

                    result2.body()?.let {

                    var generationNum=-1
                    when(it.generation.name){
                       "generation-i" ->  generationNum = 1
                        "generation-ii" ->  generationNum = 2
                        "generation-iii" ->  generationNum = 3
                        "generation-iv" ->  generationNum = 4
                        "generation-v" ->  generationNum = 5
                        "generation-vi" ->  generationNum = 6
                        "generation-vii" ->  generationNum = 7
                        "generation-viii" ->  generationNum = 8
                        "generation-ix" ->  generationNum = 9
                   }

                    var pokedexEntry:String = " "
                        var i2:Int = 0
                        if(it.flavor_text_entries.size>0){
                        while(i2<it.flavor_text_entries.size-1){
                            if(it.flavor_text_entries[i2].language.name =="en"){
//                            Log.d("lan",it.flavor_text_entries[i2].language.name)
                                break;
                            }
                            i2++


                        }}

                    val capture_rate:Int
                        val growth_rate:String
                        if(it.flavor_text_entries.isNotEmpty()) {

                            pokedexEntry = it.flavor_text_entries[i2].flavor_text
                        }
                        capture_rate = it.capture_rate
                        growth_rate = it.growth_rate.name
                result.body()?.let { Log.d("test5", it.types[0].type.name+" "+it.name)
                    var type2: String

                    if(it.types.size>1){
                         type2 = it.types[1].type.name
                    }
                    else  type2 = "null"
                    var sprite:String =""
                    if(it.sprites.other.text.frontdefault!= null)
                        sprite = it.sprites.other.text.frontdefault
                    PokemonObject._pokeList.value = PokemonObject.pokeList.value.toMutableList().apply {
                        add(Pokemon(it.name.replaceFirstChar { it.uppercase() }, sprite, it.id,it.types[0].type.name,type2, pokedexEntry,capture_rate,growth_rate,it.stats[0].base_stat,it.stats[1].base_stat,it.stats[2].base_stat,it.stats[3].base_stat,it.stats[4].base_stat,it.stats[5].base_stat,generationNum))
                    } as ArrayList<Pokemon>
                }}

                    PokemonObject.count++
                    i++
                }


            }


        }

    }}



