package com.example.pokedex.presentation.userInterface.whosthat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.theme.Font
import com.example.pokedex.presentation.userInterface.HomePage.getTypeIconwithID
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WTPGame(navController: NavHostController, viewModel: WhosThatPokemonViewModel) {
    val pokemonInfo = viewModel.pokemon.value
    val isGuessCorrect = viewModel.isGuessCorrect
    var showIncorrectMessage by remember{ mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState(), true)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navController.navigate(Route.POKEDEX.path) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Who's that Pokemon?!",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }



        pokemonInfo?.sprites?.other?.text?.frontdefault?.let { imageUrl ->
            val imageModifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally)
                .then(
                    if (!isGuessCorrect) {
                        Modifier.drawWithContent {
                            drawContent()
                            drawRect(
                                color = Color.Black.copy(alpha = 0.8f),
                                size = this.size
                            )
                        }
                    } else Modifier
                )


            AsyncImage(
                model = imageUrl, contentDescription = "Pokemon",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .then(imageModifier)
            )
        }


        /* pokemonInfo?.sprites?.other?.text?.frontdefault?.let{imageUrl ->
            val imageModifier = if(!isGuessCorrect){
                Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer { colorFilter = ColorFilter.tint(Color.Black) }
                } else {
                Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)}
            AsyncImage(model = imageUrl, contentDescription = "Whos That Pokemon Image",
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally)
                .then(imageModifier)
            )
        }*/
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = viewModel.guessAttempt, onValueChange = { viewModel.guessAttempt = it },
                label = { Text("Enter Pokemon") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp)
            )
            Button(
                onClick = { viewModel.checkGuess() },
                modifier = Modifier.padding(vertical = 18.dp)
            ) {
                Text("Enter")
            }

            Button(
                onClick = { viewModel.resetGame() },
            )
            {
                Text("Try a different Pokemon?")
            }


            if (isGuessCorrect) {
                Text("That's Right! It's ${pokemonInfo?.name}")
                Button(onClick = { viewModel.resetGame() }) {
                    Text("Play again?")
                }
            } else {

                }
            }
        }
    }



/*Text(
        text = "Who's that Pokemon?",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.align(Alignment.CenterVertically),
        color = Color.Black
    )
}*/
        /*Image(
            painter = painterResource(id = R.drawable.raichu),
            contentDescription = "raichu",
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally)
        )*/
        /*Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter Pokemon") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            )
            Button(
                onClick = {
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Enter")
            }
        }*/




