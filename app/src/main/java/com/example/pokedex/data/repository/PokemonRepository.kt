package com.example.pokedex.data.repository

import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.Gender
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.data.remote.PokemonAPI
import com.example.pokedex.domain.PokemonRepository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {

    val baseUrl = "https://pokeapi.co/api/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
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
    val gender_rate: Int,
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
    val stats: List<Stats>,
    val abilities: List<Abilities>
)

data class Abilities(
    val ability: Ability
)
data class Ability(
    val name: String
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

data class GenderRate(
    val gender: Gender,
    val maleRatio: Double,
    val femaleRatio: Double
)


class RepositoryImpl: PokemonRepository {
    var _pokeList = MutableStateFlow(ArrayList<Pokemon>())
    var pokeList = _pokeList.asStateFlow()
    var count = 0

    var filter:Boolean = false
    var _faveList = MutableStateFlow(ArrayList<Pokemon>())
    var faveList = _faveList.asStateFlow()
    var eveList = Array(549) {Array(3) {ArrayList<String>()} }
    var _filteredList = MutableStateFlow(ArrayList<Pokemon>())
    var filteredList = _filteredList.asStateFlow()

  override suspend fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){


      val quotesApi = RetrofitBase.getInstance().create(PokemonAPI::class.java)
      val speciesApi = RetrofitBase.getInstance().create(PokemonAPI::class.java)
      val eveApi = RetrofitBase.getInstance().create(PokemonAPI::class.java)

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

                        eveList[i][0].add(it.chain.species.name)

                        var i2: Int =0
                        var i3: Int =0


                        while( i2<it.chain.evolves_to.size){
                            eveList[i][1].add(it.chain.evolves_to[i2].species.name)
                            i3 =0
                            while( i3<it.chain.evolves_to[i2].evolves_to.size) {

                                eveList[i][2].add(it.chain.evolves_to[i2].evolves_to[i3].species.name)
                                i3++
                            }
                            i2++
                        }
                        Log.d("eve",eveList[i][0].toString() +" -> "+eveList[i][1].toString()+" -> "+ eveList[i][2])
                        eveList





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
                        val genderInfo= calculateGenderRate(it.gender_rate)

                result.body()?.let { Log.d("test5", it.types[0].type.name+" "+it.name)
                   var i3=0

                    var abilities = ArrayList<String>()
                    Log.d("abil",""+it.abilities.size)
                   while(i3<it.abilities.size){
                       abilities.add(it.abilities[i3].ability.name)
                       Log.d("abil",abilities[i3])
                       i3++
                   }

                    var type2: String

                    if(it.types.size>1){
                         type2 = it.types[1].type.name
                    }
                    else  type2 = "null"


                    var sprite:String =""
                    if(it.sprites.other.text.frontdefault!= null)
                        sprite = it.sprites.other.text.frontdefault
                    _pokeList.value = _pokeList.value.toMutableList().apply {
                        add(Pokemon(it.name.replaceFirstChar { it.uppercase() }, sprite, it.id,it.types[0].type.name,type2, pokedexEntry,capture_rate,growth_rate, genderRate = genderInfo,it.stats[0].base_stat,it.stats[1].base_stat,it.stats[2].base_stat,it.stats[3].base_stat,it.stats[4].base_stat,it.stats[5].base_stat,generationNum,abilities))
                    } as ArrayList<Pokemon>
                }}

                    count++

                    i++
                }





        }

    }}

private fun calculateGenderRate(genderRate: Int): GenderRate {
    return when (genderRate) {
        -1 -> GenderRate(Gender.NONE, 0.0, 0.0)
        0 -> GenderRate(Gender.MALE, 100.0, 0.0)
        8 -> GenderRate(Gender.FEMALE, 0.0, 100.0)
        in 1..7 -> {
            val femaleRatio =genderRate * 12.5
            val maleRatio= 100.0 - femaleRatio
            val gender=when{
                genderRate< 4 ->Gender.MALE
                genderRate > 4->Gender.FEMALE
                else ->Gender.MIXED
            }
            GenderRate(gender, maleRatio, femaleRatio)
        } else -> GenderRate(Gender.UNKNOWN, 0.0, 0.0)
    }
}


