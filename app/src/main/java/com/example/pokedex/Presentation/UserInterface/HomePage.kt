package com.example.pokedex.Presentation.UserInterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pokedex.Presentation.theme.Font
import com.example.pokedex.Data.Pokemon
import com.example.pokedex.R
import com.example.pokedex.navigation.Route
import com.example.pokedex.viweModel.searchPageViewModel


@Composable
fun homePage(navController: NavHostController,viewModel: searchPageViewModel) {




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
                text = "Pokédex",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(81.dp))
            Image(painter = painterResource(id = R.drawable.img_filter),
                contentDescription = "filter", modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate(Route.Filter.path)

                    }
                  )

            Spacer(modifier = Modifier.width(34.dp))

            Icon(imageVector = Icons.Default.Search, contentDescription = "search",
                modifier = Modifier.clickable {
                    navController.navigate(Route.Search.path)


                })
        }
        PokemonList(navController,viewModel,false)
    }
}


@Composable
fun PokemonList(navController: NavHostController,viewModel: searchPageViewModel,isFavorite: Boolean) {
    val pokemons = viewModel.getMockData(isFavorite)

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(pokemons.chunked(2)) { chunkedPokemons ->
            Row(
                modifier = Modifier
                    .height(178.dp)
                    .fillMaxWidth()
                    .padding(0.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                chunkedPokemons.forEach { pokemon ->
                    pokemonBox(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = Color.Black
                            )
                            .padding(4.dp),
                        navController,
                        pokemon,viewModel
                    )
                }
            }
        }
    }
}
val types = listOf(
    R.drawable.bug,
    R.drawable.dar,
    R.drawable.dra,
    R.drawable.ele,
    R.drawable.fai,
    R.drawable.fig,
    R.drawable.fir,
    R.drawable.fly,
    R.drawable.gho,
    R.drawable.gra,
    R.drawable.gro,
    R.drawable.ice,
    R.drawable.nor,
    R.drawable.poi,
    R.drawable.psy,
    R.drawable.roc,
    R.drawable.ste,
    R.drawable.wat
)

@Composable
fun pokemonBox(modifier: Modifier,
               navController: NavHostController, pokemon: Pokemon, viewModel: searchPageViewModel) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clickable {


               viewModel.setPokemon(pokemon)
                navController.navigate(Route.Pokemon.path)
            }
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "#"+pokemon.id,
                fontSize = 12.sp,
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.W400,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .padding(start = 7.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = pokemon.name,
                    fontSize = 22.sp,
                    fontFamily = Font.rudaFontFamily,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Start
                )
                // later should replace with a for each

Row() {

    val TypeString = ""

// Using a for loop
    for (i in 0 until minOf(3, TypeString.length)) {
        val currentChar = TypeString[i]
        if (TypeString.equals(types)) {

        }
    }
    Image(
        imageVector = Icons.Default.Face,
        contentDescription = "type",
        modifier = Modifier.size(25.dp)
    )
    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = "type",
        modifier = Modifier.size(25.dp)
    )
}
            }


            val picturemodifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .weight(1f)
                .fillMaxSize()
            println(pokemon.name)
            pokemonPictureAndLogo(modifier = picturemodifier,pokemon,   viewModel)
        }

    }
}

@Composable
fun pokemonPictureAndLogo(modifier: Modifier, pokemon: Pokemon, viewModel: searchPageViewModel){
    Box(
        modifier=modifier

    ) {

        AsyncImage(
            model = pokemon.pictureURL,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Icon(
            painter = painterResource(id = R.drawable.pokeball_notfave),
            //imageVector = Icons.Default.Favorite,
            contentDescription = "pokeball",
            tint=if (viewModel.PokemonsFave.contains(pokemon)) Color.Red else Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(22.dp)
                .clickable {

                    viewModel.toggleFavourite(pokemon)


                }


        )
    }

}

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        val tabs = listOf(
            Tab(
                title = "Pokedex",
                icon = Icons.Default.List,
                rootRoute = Route.POKEDEX
            ),
            Tab(
                title = "Favorites",
                icon = Icons.Default.Favorite,
                rootRoute = Route.FAVORITES
            )
        )

        val isFavoritesTabSelected = navController.currentBackStack
            .collectAsState()
            .value
            .any { it.destination.route == Route.FAVORITES.path }

        tabs.forEach { tab ->
            val isTabSelected = if (tab.rootRoute == Route.POKEDEX) {
                !isFavoritesTabSelected
            } else {
                isFavoritesTabSelected
            }

            NavigationBarItem(
                icon = {
                    Icon(imageVector = tab.icon, contentDescription = null)
                },
                label = { Text(text = tab.title) },
                selected = isTabSelected,
                onClick = {
                    navController.navigate(route = tab.rootRoute.path) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    //navController.navigate(route = tab.rootRoute.path)
                }
            )
        }
    }
}
private data class Tab(
    val title: String,
    val icon: ImageVector,
    val rootRoute: Route
)

@Preview(showBackground = true)
@Composable
fun homePreview(){
    val navController = rememberNavController()
    val viewModel = viewModel<searchPageViewModel>()

    homePage(navController = navController,viewModel)

}