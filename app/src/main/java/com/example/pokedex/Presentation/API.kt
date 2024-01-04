package com.example.pokedex.viweModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.Data.Pokemon
import com.example.pokedex.Gender
import com.example.pokedex.PokemonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class ApiViewModel: ViewModel() {
//    load image
    //imageView.load("https://example.com/image.jpg")

  fun addPokemon(start:Int, end:Int, onlyDefaults:Boolean, cleanCopy:Boolean){
        viewModelScope.launch(Dispatchers.IO){
//            val fileName = "json/pokemonCache.json"
//            var file = File(fileName)
//            var cacheJson = JSONObject(file.readText())
            val limit = start+end
//            val jsonData =URL("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$start").readText()
//            val pokemonJson = JSONObject(jsonData)
            var i : Int = start

            while(i <= end){
////            if(cleanCopy and (!cacheJson.has("$i"))) {
//
                val jsonData2 = URL("https://pokeapi.co/api/v2/pokemon/$i").readText()
                val jsonDataSpecies = URL("https://pokeapi.co/api/v2/pokemon-species/$i").readText()
////            }
                var pokemonJson2  = JSONObject(jsonData2)
                var pokemonJsonSpecies = JSONObject(jsonDataSpecies)
                if (pokemonJson2.getBoolean("is_default") or !onlyDefaults) {
                    var pokeName: String =
                        pokemonJson2.getString("name")
                    var pokeId: Int =
                        pokemonJson2.getInt("id")
                    var pokeDefaultPictureFront: String =
                        pokemonJson2.getJSONObject(
                            "sprites"
                        ).getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
                    var type1: String =pokemonJson2.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")



                    var type2: String = "null"
                    if (pokemonJson2.getJSONArray("types").length()>1) {
                        type2 = pokemonJson2.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name")
                    }
                    var pokedexTextList = ArrayList<String>()
                    pokedexTextList.add(pokemonJsonSpecies.getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text"))

                        Log.d("info",""+pokeName+" "+pokeDefaultPictureFront+" "+ pokeId+" "+  type1+" "+type2)

                    val genderRate=pokemonJsonSpecies.getInt("gender_rate")
                    val gender=when{
                        genderRate<0 -> Gender.NONE
                        genderRate ==0 -> Gender.MALE
                        genderRate == 8 -> Gender.FEMALE
                        genderRate in 1..3 ->Gender.MALE
                        genderRate == 4 -> Gender.MIXED
                        genderRate in 5..7 -> Gender.FEMALE
                        else -> Gender.UNKNOWN
                    }

                    PokemonObject.pokeList.add(Pokemon(pokeName.replaceFirstChar { it.uppercase() }, pokeDefaultPictureFront, pokeId,type1,type2, pokedexTextList,gender))
//                cacheJson.put(""+pokeId,pokeName)
                }

                i++
        }

        }

    }}



