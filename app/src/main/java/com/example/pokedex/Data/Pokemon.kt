package com.example.pokedex.Data

import com.example.pokedex.Gender

 data class Pokemon(
  val name: String,
  val pictureURL: String,
  val id: Int,
  val type1: String,
  val type2: String,
  val pokedexText: ArrayList<String>,
  val gender: Gender,
  val maleRatio: Double,
  val femaleRatio: Double
 )