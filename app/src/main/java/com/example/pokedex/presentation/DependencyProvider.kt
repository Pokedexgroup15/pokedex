package com.example.pokedex.presentation

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.local.PokemonDatabase


object DependencyProvider {
    lateinit var applicationContext: Context
        private set



fun initialize(context: Context) {
  applicationContext = context.applicationContext
}
fun getcontext(): Context {

  return applicationContext
}
}