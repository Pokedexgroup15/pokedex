package com.example.pokedex.data

import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.Gender
import com.example.pokedex.data.local.PokemonDAO
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.domain.PokemonForm
import com.example.pokedex.presentation.PokemonObject
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

interface PokeEveChain{
    @GET("evolution-chain/{id}")

    suspend fun getPokemonEveInfo(@Path("id") pathId: Int) : Response<PokemonEve>
}

interface PokeAbility{
    @GET("ability/{id}")

    suspend fun getPokemonAbilInfo(@Path("id") pathId: Int) : Response<PokeAbil>
}
interface PokeForms{
    @GET("pokemon-form/{id}")

    suspend fun getPokemonFormInfo(@Path("id") pathId: Int) : Response<PokeForm>
}

data class PokeForm(
    val sprites : sprite,
    val name :String,
    val types:List<subType>,
    val id: Int
)

data class PokemonEve(
    val chain: Chain

)

data class PokeAbil(
    val name:String,
    val effect_entries :List<Ent>
)

data class Ent(
    val effect : String,
    val language: Language
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
    val generation: Generation,
    val varieties: List<Varieties>
)

data class Varieties(
    val pokemon: Species

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
    val abilities: List<Abilities>,
    val forms: List<Species>
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
    val other: Other,
    val front_default: String?
)
data class Other(
    @SerializedName("official-artwork")
    val official : Official,
    val home : Official

)
data class Official(
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





class RepositoryImpl(  private val dao: PokemonDAO): ViewModel() {
//    load image
    //imageView.load("https://example.com/image.jpg")

  fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){


    val quotesApi = RetrofitBase.getInstance().create(PokeApi::class.java)
    val speciesApi = RetrofitBase.getInstance().create(PokeApiSpecies::class.java)
    val eveApi = RetrofitBase.getInstance().create(PokeEveChain::class.java)
    val abilApi = RetrofitBase.getInstance().create(PokeAbility::class.java)
    val formApi =   RetrofitBase.getInstance().create(PokeForms::class.java)

        viewModelScope.launch(Dispatchers.IO){
//            val fileName = "json/pokemonCache.json"
//            var file = File(fileName)
//            var cacheJson = JSONObject(file.readText())
//            val jsonData =URL("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$start").readText()
//            val pokemonJson = JSONObject(jsonData)
            var i : Int = start
            GlobalScope.launch {
                while(i <= end) {

                    while(i<= PokemonObject.tempEnd||(PokemonObject.filter&&PokemonObject.count<PokemonObject.tempEnd)){
                        Log.d("pag"," filterSize "+PokemonObject.filteredList.value.size)
                    var j =i+10000
                    if(j<=10448){
                        val resultForm = formApi.getPokemonFormInfo(j)
                        resultForm.body()?.let {
                            if (it.sprites.front_default != null) {
                                var type2 ="null"
                                if(it.types.size>1){
                                    type2 = it.types[1].type.name
                                }
                                PokemonObject.formMap.put(it.name,PokemonForm(it.name,it.sprites.front_default,it.id,it.types[0].type.name,type2))
                            }
                        }
                        if(j<=10277) {
                            val result = quotesApi.getPokemonInfo(j)
                            result.body()?.let {
                                if (it.sprites.other.official.frontdefault != null) {
                                    var type2 ="null"
                                    if(it.types.size>1){
                                        type2 = it.types[1].type.name
                                    }
                                    PokemonObject.formMap.put(it.name, PokemonForm(it.name,it.sprites.front_default,it.id,it.types[0].type.name,type2))
                                }
                            }
                        }
                    }


                    var forms = ArrayList<String>()

                    val result = quotesApi.getPokemonInfo(i)
                val result2 = speciesApi.getPokemonSpeciesInfo(i)
                    val resultForm = formApi.getPokemonFormInfo(i)
                    Log.d("form2", ""+resultForm)
                    Log.d("form2", ""+resultForm.body())
                    Log.d("form2",""+resultForm.body()?.sprites)

                   resultForm.body()
                          ?.let {
                              if(it.sprites.front_default!=null) {
                                  var type2 ="null"
                                  if(it.types.size>1) {
                                      type2 = it.types[1].type.name
                                  }
                                   PokemonObject.formMap[resultForm.body()!!.name] = PokemonForm(it.name,it.sprites.front_default,it.id,it.types[0].type.name,type2)
                                   Log.d("form2", it.name)
                               }
                 }


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

                    if (i<310){
                        val result4= abilApi.getPokemonAbilInfo(i)
                        var i2=0
                        result4.body()?.let {


                            while(i2<it.effect_entries.size){

                                if(it.effect_entries[i2].language.name=="en"){
                                    PokemonObject.abilMap.put(it.name,it.effect_entries[i2].effect)
                                    break
                                }
                                i2++
                            }

                        }


                        }

                    result2.body()?.let {

                        var i3=0
                        while(i3<it.varieties.size){
                            forms.add(it.varieties[i3].pokemon.name)
""
                            i3++
                        }

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
                        val genderInfo=calculateGenderRate(it.gender_rate)

                result.body()?.let { Log.d("test5", it.types[0].type.name+" "+it.name)
                    var i4 = 0
                    while (i4<it.forms.size){
                        if(it.forms[i4].name!= result.body()!!.name){
                            forms.add(it.forms[i4].name)
                        }
                        i4++
                    }

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
                    if(it.sprites.other.official.frontdefault!= null)
                        sprite = it.sprites.other.official.frontdefault
                    else
                        sprite = it.sprites.other.home.frontdefault
                    var pk=Pokemon(it.name.replaceFirstChar { it.uppercase() }, sprite, it.id,it.types[0].type.name,type2, pokedexEntry,capture_rate,growth_rate, genderRate = genderInfo,it.stats[0].base_stat,it.stats[1].base_stat,it.stats[2].base_stat,it.stats[3].base_stat,it.stats[4].base_stat,it.stats[5].base_stat,generationNum,abilities,forms)

                    //val st= serializeToJson(pk)
                  //  var pk2:LocalPokemon= LocalPokemon(it.id,st,it.types[0].type.name,type2,generationNum,capture_rate,growth_rate)
                    //dao.insert(pk2)


                    PokemonObject._pokeList.value = PokemonObject._pokeList.value.toMutableList().apply {
                        add(pk)
                    } as ArrayList<Pokemon>
                }}

Log.d("inf",""+i)
                    i++
                }}

            }


        }

    }}

private fun calculateGenderRate(genderRate: Int): GenderRate {
    return when (genderRate) {
        -1 ->GenderRate(Gender.NONE, 0.0, 0.0)
        0 ->GenderRate(Gender.MALE, 100.0, 0.0)
        8 ->GenderRate(Gender.FEMALE, 0.0, 100.0)
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

fun InternetIsConnected(): Boolean {
    return try {
        val command = "ping -c 1 google.com"
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        false
    }
}

private fun serializeToJson(pokemon: Pokemon): String {
    val gson = Gson()
    return gson.toJson(pokemon)
}