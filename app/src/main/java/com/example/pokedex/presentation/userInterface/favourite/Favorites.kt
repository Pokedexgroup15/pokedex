package com.example.pokedex.presentation.userInterface.favourite
import com.example.pokedex.presentation.theme.Font.Companion
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.HomePage.PokemonList
import com.example.pokedex.R
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.userInterface.filterPage.FilterViewModel
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.theme.Font
import com.example.pokedex.presentation.userInterface.filterPage.SortOption


//Placeholder
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun Favorites(navHostController: NavHostController, viewModel: searchPageViewModel, filterViewModel: FilterViewModel, boolean: Boolean = PokemonObject.filterFaveBool) {
    val currentSortOption = filterViewModel.selectedSortOption.value
    val pokemonsFaveList by viewModel.PokemonsFave.collectAsState(initial = arrayListOf())

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
            Spacer(modifier = Modifier.width(81.dp))
            Image(painter = painterResource(id = R.drawable.img_filter),
                contentDescription = "filter", modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navHostController.navigate(Route.Filter.path)

                    }
            )
        }
        if (pokemonsFaveList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "No favorites selected yet",
                    fontSize = 20.sp,
                    fontFamily = Companion.rudaFontFamily,
                    color = Color.Gray
                )
            }
        } else {
            PokemonList(
                navController = navHostController,
                viewModel = viewModel,
                isFavorite = true,
                sortOption = currentSortOption
            )
        }
    }
}