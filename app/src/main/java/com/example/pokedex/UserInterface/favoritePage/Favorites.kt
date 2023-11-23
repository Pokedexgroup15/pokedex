package com.example.pokedex.UserInterface.favoritePage

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.R
import com.example.pokedex.UserInterface.PokemonList
import com.example.pokedex.UserInterface.favoritePage.FavoritesViewModel
import com.example.pokedex.data.MockFavoritesDataSource


//Placeholder
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Favorites() {
//    val favoritesViewModel: FavoritesViewModel = viewModel()
  //  val favorites by favoritesViewModel.favorites.collectAsState(emptyList())


    //PokemonList(favorites = favorites, onFavoriteButtonClicked = favoritesViewModel::onFavoriteButtonClicked)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(71.dp))
            Text(
                text = "faves",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(81.dp))
            Image(
                painter = painterResource(id = R.drawable.img_filter),
                contentDescription = "filter", modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(34.dp))
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")

            //LazyColumn{
                //items(favorites) {pokemon ->
               // }
           // }

        }
    }
}


