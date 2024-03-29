package com.example.pokedex.presentation.userInterface.HomePage
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.presentation.PokemonObject
import com.example.pokedex.R
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.theme.Font


@Composable
fun pokemonPage(navController: NavHostController){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        ){
            Text(text = "", modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.weight(4f))
//            EvolutionBar(navController, pokemon  )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EvolutionBar(navController: NavHostController,pokemon: Pokemon,viewModel: searchPageViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 10.dp)
    ) {
        Text(
            text = "Evolutions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Font.rudaFontFamily,
            modifier = Modifier
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    )
    {

        var i: Int = 0
        var matchFound: Boolean = false

        while (i < PokemonObject.eveList.size&& !matchFound) {
            var i0: Int = 0
            var i1: Int = 0
            var i2: Int = 0
            while (i0 < PokemonObject.eveList[i][0].size && !matchFound) {
                Log.d("eve2",""+pokemon.name +" = "+PokemonObject.eveList[i][0][i0])

                if (pokemon.name.lowercase() == PokemonObject.eveList[i][0][i0]) {
                    matchFound = true
                    Log.d("eveli",""+matchFound)

                    break
                }
                i0++
            }
            while (i1 < PokemonObject.eveList[i][1].size&& !matchFound) {
                Log.d("eve2",""+pokemon.name +" = "+PokemonObject.eveList[i][1][i1])

                if (pokemon.name.lowercase() == PokemonObject.eveList[i][1][i1]) {
                    matchFound = true
                    Log.d("eveli",""+matchFound)

                    break
                }
                i1++
            }

            while (i2 < PokemonObject.eveList[i][2].size&& !matchFound) {
                Log.d("eve2",""+pokemon.name +" = "+PokemonObject.eveList[i][2][i2])

                if (pokemon.name.lowercase() == PokemonObject.eveList[i][2][i2]) {
                    matchFound = true
                    Log.d("eveli",""+matchFound)

                    break
                }
                i2++
            }
if(!matchFound) {
    i++
}
        }
        var i4: Int = 0
        var i5: Int = 0
        var i6: Int = 0

        if( matchFound) {
            Log.d("eveli2","check1")
            while(i4<PokemonObject.eveList[i][0].size) {
              var imageUrl= ""
                var i7: Int = 0
                var curPokemon = pokemon

                while(i7< PokemonObject.pokeList.value.size){
                    if (PokemonObject.pokeList.value[i7].name.lowercase()==PokemonObject.eveList[i][0][i4]){
                        imageUrl = PokemonObject.pokeList.value[i7].pictureURL
                        curPokemon = PokemonObject.pokeList.value[i7]

                        break
                    }
                  i7++
              }
                EvolutionCircleImage(navController, imageUrl,viewModel,curPokemon)
                i4++
            }
            if (PokemonObject.eveList[i][1].size !=0){
            EvolutionArrowImage()
            while(i5<PokemonObject.eveList[i][1].size){
                Log.d("eveli2","check2")

                var imageUrl= ""
                var i7: Int = 0
                var curPokemon = pokemon

                while(i7< PokemonObject.pokeList.value.size){
                    if (PokemonObject.pokeList.value[i7].name.lowercase()==PokemonObject.eveList[i][1][i5]){
                        imageUrl = PokemonObject.pokeList.value[i7].pictureURL
                        curPokemon = PokemonObject.pokeList.value[i7]
                        break
                    }
                    i7++
                }

                EvolutionCircleImage(navController, imageUrl,viewModel,curPokemon)
                i5++
            }
                if (PokemonObject.eveList[i][2].size !=0) {

                    EvolutionArrowImage()
                    while (i6 < PokemonObject.eveList[i][2].size) {

                        var imageUrl= ""
                    var i7: Int = 0
                        var curPokemon =pokemon
                    while(i7< PokemonObject.pokeList.value.size){
                        Log.d("eveli2","check3")

                        if (PokemonObject.pokeList.value[i7].name.lowercase()==PokemonObject.eveList[i][2][i6]){
                            imageUrl = PokemonObject.pokeList.value[i7].pictureURL
                            curPokemon = PokemonObject.pokeList.value[i7]
                            break
                        }
                        i7++
                    }

                        EvolutionCircleImage(navController, imageUrl,viewModel,curPokemon)
                        i6++
                    }
                }
        }
    }
        /*repeat(3){
            EvolutionCircleImage()
            }*/
    }
}


@Composable
fun EvolutionCircleImage(navController: NavHostController,imageUrl: String,viewModel: searchPageViewModel,pokemon:Pokemon) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .clip(CircleShape)
            .clickable() {
                viewModel.setPokemon(pokemon)
                navController.navigate(Route.Pokemon.path)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.evolutionscircle),
            contentDescription = "Evolution",
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp),
            //contentScale = ContentScale.Crop
            //modifier = Modifier.size(100.dp)
        )
        EvolutionsPicture(imageUrl)
    }
}


@Composable
fun EvolutionArrowImage(){
    Image(
        painter = painterResource(id = R.drawable.evolutionvector),
        modifier = Modifier
            .size(25.dp)
            .offset(y = 30.dp),
        contentDescription = null
    )
}

@Composable
fun DropDownArrowImage(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.dropdownevolutionvector),
        modifier = Modifier
            .size(25.dp)
            .offset(y = 2.dp),
        contentDescription = null
    )
}
//
@Composable
fun EvolutionsPicture(imageUrl: String ){
    AsyncImage(
        model = imageUrl, contentDescription = "Pokemon",
        modifier = Modifier
            .size(65.dp)
            .offset(y = -10.dp)
            .offset(x = 3.dp),
    )
}

@Composable
fun VersionBar(navController: NavHostController, viewModel: searchPageViewModel) {
    var isButtonClicked by remember { mutableStateOf(false) }
    val pokemon = viewModel.getPokemon()
    val versions = pokemon?.forms ?: emptyList()
    val (selectedVersion, setSelectedVersion) = remember { mutableStateOf<String?>(null) }

    Column {
        Row {
            Text(
                text = "Versions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Font.rudaFontFamily,
                modifier = Modifier.padding(10.dp)
            )
        }
        Column {
            if (pokemon != null) {
                versions.forEach { version ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = version,
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp),
                            fontFamily = Font.rudaFontFamily
                        )
                        IconButton(onClick = {
                            // Toggle the selected version when the button is clicked
                            if (selectedVersion == version) {
                                setSelectedVersion(null)
                            } else {
                                setSelectedVersion(version)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info"
                            )
                        }
                    }

                    // Display the picture of the Pokémon when the "Info" icon is clicked for the selected version
                    if (selectedVersion == version) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
                        )
                        if (PokemonObject.formMap[version] != null) {
                            Row {
                                if (PokemonObject.formMap[version]?.type1 != null) {
                                    Image(
                                        painter = painterResource(
                                            id = getTypeIconwithID(
                                                PokemonObject.formMap[version]?.type1.toString()
                                            )
                                        ),
                                        contentDescription = "type1",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                if (PokemonObject.formMap[version]?.type2 != "null") {
                                    Image(
                                        painter = painterResource(
                                            id = getTypeIconwithID(
                                                PokemonObject.formMap[version]?.type2.toString()
                                            )
                                        ),
                                        contentDescription = "type2",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }


                                Box(
                                    modifier = Modifier.padding(10.dp), // Increase the padding value as needed
                                ) {
                                    var imageUrl = pokemon.pictureURL
                                    Log.d("form", "" + version)
                                    Log.d("form", "" + PokemonObject.formMap[version])
                                    if (PokemonObject.formMap[version] != null) {
                                        imageUrl =
                                            PokemonObject.formMap[version]?.pictureURL.toString()
                                    }


                                    EvolutionsPicture(imageUrl)

                                    // Add Pokémon type icons


                            }

                        }
                    }
                }
            }
        }
    }
