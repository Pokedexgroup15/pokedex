package com.example.pokedex.domain

data class Pokemon(
  val name: String,
  val pictureURL: String,
  val id: Int,
  val type1: String,
  val type2: String,
  val pokedexText: ArrayList<String>,
  val evolution:ArrayList<String>

 )
