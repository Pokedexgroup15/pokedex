package com.example.pokedex.presentation.userInterface.SearchPage

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.MainActivity
import com.example.pokedex.presentation.PokemonObject
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.navigation.Route

import com.example.pokedex.presentation.searchPageViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchPageFun(navController: NavHostController, viewModel: searchPageViewModel) {
    var name by remember { mutableStateOf("") }
    var Pokemons= viewModel.getData(false,PokemonObject.filterFaveBool)
    val context = LocalContext.current // Get the current context
    var searchList by remember { mutableStateOf(mutableListOf<Pokemon>()) }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(243, 237, 247))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0), RoundedCornerShape(15.dp))
                .height(86.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "backArrow",
                  modifier =  Modifier.clickable {
                      navController.popBackStack()

                    }
                      .size(36.dp))

            TextField(
                value = name,
                onValueChange = { text -> name = text },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Search your Pokemon") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchList.clear()
                        name.trim()
                        searchList = Pokemons.value.filter { it.name.startsWith(name.replaceFirstChar { it.uppercase() }) }.toMutableList()
                    }
                )
            )
            if (name.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear",
                    modifier = Modifier.clickable {
                        name = ""
                    }
                )
            }
        }

        Pokemonlists(searchList,navController,viewModel)
    }
}

@Composable
fun Pokemonlists(pokeList:List<Pokemon>,
                 navController: NavHostController, viewModel: searchPageViewModel
) {


    LazyColumn() {


        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        items(pokeList) { currentPokemon ->

            Row(
                verticalAlignment = Alignment.CenterVertically
                ,
                modifier = Modifier.clickable {
                    viewModel.setPokemon(currentPokemon)
                    navController.navigate(Route.Pokemon.path)
                }
            ) {



                Spacer(modifier = Modifier.width(16.dp))
                Box (modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color(0, 0, 0, 20),
                        shape = CircleShape

                    )
                    .size(64.dp)


                ) {
                    AsyncImage(
                        model = currentPokemon.pictureURL,
                        contentDescription = "currentPokemon",
                        alignment = Alignment.Center,
                        modifier= Modifier
                            .background(Color.Transparent)
                            .size(64.dp)
                            .clip(shape = CircleShape)

                    )


                }

                Spacer(modifier= Modifier.width(25.02.dp))

                Text(
                    text = currentPokemon.name,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default

                )

            }
            Spacer(modifier= Modifier.height(39.dp))

        }
    }


}

