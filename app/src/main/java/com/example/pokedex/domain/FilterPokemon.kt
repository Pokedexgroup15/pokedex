package com.example.pokedex.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.pokedex.PokemonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList

class FilterPokemon {

    fun filterList(selectedTypes: MutableState<List<Int>>, selectedCatchRate: MutableState<List<String>>, selectedGeneration: MutableState<Int>){

        if(filterActive(selectedTypes,selectedCatchRate,selectedGeneration)){
        PokemonObject.filteredList.value.clear()
            var i = 0
        Log.d("filter"," "+selectedTypes.value+" "+ selectedCatchRate.value +" "+ selectedGeneration.value)
       while( PokemonObject.pokeList.value.size>i){
           if((PokemonObject.pokeList.value[i].generation ==selectedGeneration.value || selectedGeneration.value==-1) &&((selectedTypes.value.isEmpty()) ||(selectedTypes.value.contains(convertNumberType(PokemonObject.pokeList.value[i].type1))&&(selectedTypes.value.size!=2)) ||(selectedTypes.value.contains(convertNumberType(PokemonObject.pokeList.value[i].type2)))&&(selectedTypes.value.size!=2)||((selectedTypes.value.contains(convertNumberType(PokemonObject.pokeList.value[i].type1)))&&((selectedTypes.value.contains(convertNumberType(PokemonObject.pokeList.value[i].type2)))))) && (selectedCatchRate.value.isEmpty() || selectedCatchRate.value.contains(convertNumberGrowthRate(PokemonObject.pokeList.value[i].growth_rate)) )) {

               PokemonObject._filteredList.value = PokemonObject.filteredList.value.toMutableList().apply {
                   add(PokemonObject.pokeList.value[i])
               } as ArrayList<Pokemon>
               Log.d("filter", "" + PokemonObject.pokeList.value[i].name)
           }
           i++
       }}






    }

    fun convertNumberType(input:String):Int{
        var type:Int = -1

        when(input){
            "bug" -> type = 2131034115
            "dark" -> type = 2131034118
            "dragon" -> type = 2131034120
            "electric" -> type = 2131034123
            "fairy" -> type = 2131034128
            "fighting" -> type = 2131034131
            "fire" -> type = 2131034133
            "flying" -> type = 2131034135
            "ghost" -> type = 2131034137
            "grass" -> type = 2131034139
            "ground" -> type = 2131034141
            "ice" -> type = 2131034153
            "normal" -> type = 2131034169
            "poison" -> type = 2131034184
            "pyschic" -> type = 2131034188
            "rock" -> type = 2131034192
            "steel" -> type = 2131034194
            "water" -> type = 2131034197


        }


        return type
    }
    fun convertNumberGrowthRate(input:String):String{
        var type:String = " "

        when(input){
            "slow-then-very-fast" ->type = "Erratic"
            "fast-then-very-slow" -> type = "Fluctuating"
            "medium-slow" -> type = "Medium-Slow"
            "fast" -> type = "Fast"
            "slow" -> type = "Slow"
            "medium" -> type = "Medium-Fast"


        }


        return type
    }



    fun filterActive(selectedTypes: MutableState<List<Int>>, selectedCatchRate: MutableState<List<String>>, selectedGeneration: MutableState<Int>):Boolean{
        if (!(selectedTypes.value.isEmpty() && selectedCatchRate.value.isEmpty() && selectedGeneration.value ==-1)) {
            PokemonObject.filter = true
            return true
        }
        else{
            PokemonObject.filter = false
            return true}


    }



}