package com.example.pokedex.presentation.userInterface.FilterPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex.Presentation.UserInterface.FilterPage.SortOption


class ResetViewModel : ViewModel() {
    val selectedSortOption = mutableStateOf<SortOption?>(null)
    val selectedTypes = mutableStateOf<List<Int>>(emptyList())
    val selectedGeneration = mutableStateOf(-1)
    val selectedName = mutableStateOf<String?>(null)

    fun resetFilters() {
        selectedSortOption.value = null
        selectedTypes.value = emptyList()
        selectedGeneration.value = -1
        selectedName.value = null
        // Add more reset logic as needed
    }
}