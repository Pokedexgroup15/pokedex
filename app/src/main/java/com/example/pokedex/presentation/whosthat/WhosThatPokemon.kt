package com.example.pokedex.presentation.userInterface.whosthat

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.theme.Font
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WTPGame(navController: NavHostController, viewModel: WhosThatPokemonViewModel) {
    val pokemonInfo = viewModel.pokemon.value
    val isGuessCorrect = viewModel.isGuessCorrect
    var showIncorrectMessage by remember { mutableStateOf(false) }


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

            Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                modifier=Modifier.clickable {
                    navController.popBackStack()
                })
            Text(
                text = "Who's that Pokemon?!",
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }


        pokemonInfo?.sprites?.other?.official?.frontdefault?.let { imageUrl ->
            Box(
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            ) {
                Image(
                    painterResource(id = R.drawable.whosthatpokemonbackground),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                if (!isGuessCorrect) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Pokemon",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    AsyncImage(
                        model = imageUrl, contentDescription = "Pokemon",
                        modifier = Modifier.matchParentSize()
                    )
                }
                Text(
                    text = "Score: ${viewModel.totalPoints}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
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
val keyboardController = LocalSoftwareKeyboardController.current
            Row() {
                TextField(
                    value = viewModel.guessAttempt, onValueChange = { viewModel.guessAttempt = it },
                    label = { Text("Enter Pokemon") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.checkGuess()
                        keyboardController?.hide()
                        showIncorrectMessage =
                            !viewModel.isGuessCorrect && viewModel.guessAttempt.isNotEmpty()
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent // Set your desired background color here
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .width(30.dp)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(2.dp, Color.LightGray, RoundedCornerShape(20.dp))
                )
                if (!isGuessCorrect) {
                    Button(
                        onClick = {
                            viewModel.checkGuess()
                            showIncorrectMessage =
                                !viewModel.isGuessCorrect && viewModel.guessAttempt.isNotEmpty()
                            keyboardController?.hide()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6F3FF)
                        ),
                        modifier = Modifier
                            .padding(vertical = 1.dp)
                            .wrapContentWidth()
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                    {
                        Text(
                            "Submit",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = Font.rudaFontFamily
                            //modifier = Modifier
                            //  .border(
                            //    width = 1.dp,
                            //  color = Color.Black,
                            //shape = RoundedCornerShape(20.dp)
                            //)
                            //.padding(10.dp)
                        )
                    }
                }
            }

                Divider(
                    color = Color.LightGray,
                    thickness = 1.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            // Spacer(modifier = Modifier.height(20.dp))


            if (isGuessCorrect && pokemonInfo?.name != null) {
                val displayName =
                    pokemonInfo.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        "That's Right! It's ${displayName}!",
                        fontSize = 25.sp,
                        fontFamily = Font.rudaFontFamily,
                        color = Color(0xFF38A552)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.resetGame() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6F3FF)
                    ),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(25.dp)
                        )


                ) {
                    //Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Play again?",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = Font.rudaFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

            } else {
                if (showIncorrectMessage) {
                    Text(
                        text = "That is incorrect... try again?",
                        fontSize = 25.sp,
                        fontFamily = Font.rudaFontFamily,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(5.dp))


                }

                Button(
                    onClick = {
                        viewModel.resetGame()
                        showIncorrectMessage = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6F3FF)
                    ),
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(25.dp)
                        )

                )

                {
                    Text(
                        "Try a different Pokemon?",
                        color = Color.Black,
                        fontFamily = Font.rudaFontFamily,
                        fontSize = 15.sp
                    )
                }

            }
        }

        //Spacer(modifier = Modifier.height(20.dp))


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




