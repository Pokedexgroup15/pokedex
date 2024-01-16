package com.example.pokedex.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "Favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int

    // Other fields in your Favourite entity

    // Define a relationship to the Names entity


)
/*
@Entity(tableName = "Name")
data class Name(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val favouriteId: Long, // Foreign key to link with the Favourite entity

    val name: String
)


 */