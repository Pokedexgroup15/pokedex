package com.example.pokedex.presentation.userInterface.favourite
import com.example.pokedex.presentation.theme.Font.Companion
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.presentation.userInterface.HomePage.PokemonList
import com.example.pokedex.R
import com.example.pokedex.presentation.userInterface.filterPage.FilterViewModel
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.userInterface.filterPage.SortOption


//Placeholder
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun Favorites(navHostController: NavHostController, viewModel: searchPageViewModel, filterViewModel: FilterViewModel) {
    val currentSortOption = filterViewModel.selectedSortOption.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(71.dp))

            Text(
                text = "Favorites",
                fontSize = 30.sp,

                fontWeight = FontWeight.Bold,
                fontFamily = Companion.rudaFontFamily
            )
        }
        PokemonList(navController = navHostController, viewModel = viewModel, isFavorite = true, sortOption = currentSortOption)
    }
}