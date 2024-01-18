package com.example.pokedex.presentation.navigation
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.PokemonObject
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel
import com.example.pokedex.presentation.userInterface.filterPage.FilterPageContent
import com.example.pokedex.ShowcasePage
import com.example.pokedex.data.PokeApi
import com.example.pokedex.data.RetrofitBase
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.presentation.userInterface.filterPage.FilterViewModel

import com.example.pokedex.presentation.userInterface.HomePage.BottomBar
import com.example.pokedex.presentation.userInterface.favourite.Favorites

import com.example.pokedex.presentation.userInterface.HomePage.homePage
import com.example.pokedex.presentation.userInterface.SearchPage.SearchPageFun

import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.userInterface.whosthat.WTPGame
import com.example.pokedex.presentation.userInterface.whosthat.WhosThatPokemonViewModel


@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier,
                viewModel:searchPageViewModel) {



    val filterviewModel = viewModel<FilterViewModel>()
    val resetViewModel = viewModel<ResetViewModel>()


    NavHost(
        navController = navController,
        startDestination = Route.POKEDEX.path,
        modifier = modifier
    ) {
        composable(Route.POKEDEX.path) {
            //if(filterviewModel.sortPokemonList(SortOption.LowToHigh) dash then do dash)




            homePage(navController,viewModel, filterviewModel, PokemonObject.filterFaveBool)
        }
        composable(Route.Search.path) {
            SearchPageFun(navController,viewModel )
        }
        composable(Route.FAVORITES.path) {
           Favorites(navController, viewModel, filterviewModel, PokemonObject.filterFaveBool)
        }
        composable(Route.Filter.path) {
            FilterPageContent(navController, FilterViewModel(), resetViewModel)
        }
        composable(Route.Pokemon.path) {
            ShowcasePage(navController, viewModel)
        }
        composable(Route.Game.path){
            val pokeApi = RetrofitBase.getInstance().create(PokeApi::class.java)
            WTPGame(navController, WhosThatPokemonViewModel(pokeApi))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun navStart(viewModel: searchPageViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            viewModel
        )
    }
}



