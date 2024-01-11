package com.example.pokedex



import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.data.GenderRate
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.userInterface.HomePage.EvolutionBar
import com.example.pokedex.presentation.userInterface.HomePage.getTypeIconwithID
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.theme.Font
import com.example.pokedex.presentation.userInterface.description.AbilityItem
import com.example.pokedex.presentation.userInterface.description.AbilityListWithDescription


    @Composable
    fun ShowcasePage(navController: NavHostController,viewModel: searchPageViewModel) {
        val context = LocalContext.current
        val pokemon = viewModel.getPokemon()
        val maleColor = Color(49,59,169)
        val femaleColor = Color(143,68,124)
        val mixedColor= Color(0xFFF5F5DC)
        val genderlessColor = Color.LightGray
        var catchRateTextBox by remember { mutableStateOf(false) }
        var growthRateTextBox by remember { mutableStateOf(false) }
        var Favorized by remember { mutableStateOf(viewModel.PokemonsFave.value.contains(pokemon)) }
        var isAbilityVisible by remember { mutableStateOf(false) }
        val descriptionVisibilityMap = remember { mutableStateMapOf<String, Boolean>() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState(), true)
        ) {
            // Top Baren folkens!
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.POKEDEX.path){
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
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                //Spacer(modifier = Modifier.width(14.dp))
                //Texten skal retrieve en string fra PokeAPI'en.

                viewModel.getPokemon()?.let {
                    Text(
                        text = it.name,
                        fontSize = 30.sp,
                        fontFamily = Font.rudaFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (pokemon != null) {
                            Image(
                                painter = painterResource(id = getTypeIconwithID(pokemon.type1)),
                                contentDescription = "type1",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        if (pokemon != null) {
                            if (pokemon.type2 != "null") {
                                Image(
                                    painter = painterResource(id = getTypeIconwithID(pokemon.type2)),
                                    contentDescription = "type2",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            }
            Divider(
                color = Color.Black,
                thickness = 1.5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            val backgroundColor = when (pokemon?.genderRate?.gender) {
                Gender.MIXED->mixedColor
                Gender.NONE -> genderlessColor
                Gender.MALE -> maleColor
                Gender.FEMALE -> femaleColor
                else -> Color.Transparent

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
            ) {
                if (pokemon != null) {
                    AsyncImage(
                        model = pokemon.pictureURL,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentScale = ContentScale.Crop
                    )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                //RYK GENDERICONS og Favorite ICON HER SÅ DET BLIVER IN PICTURE som FIGMA
                ////////////////////////////////////////////////////
                Spacer(modifier = Modifier.width(16.dp))

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment=Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                pokemon?.let { GenderDisplay(genderRate = it.genderRate) }

                Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pokeball_bw),
                    contentDescription = "Favorite option",
                    //tint = if (viewModel.PokemonsFave.contains(pokemon)) Color.Red else Color.Black,
                    tint = if (Favorized) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            Favorized = !Favorized
                            pokemon?.let {
                                if (Favorized) {


                                    PokemonObject._faveList.value =
                                        PokemonObject.pokeList.value
                                            .toMutableList()
                                            .apply {
                                                add(it)
                                            } as ArrayList<Pokemon>
                                } else {
                                    PokemonObject._faveList.value =
                                        PokemonObject.pokeList.value
                                            .toMutableList()
                                            .apply {
                                                add(it)
                                            } as ArrayList<Pokemon>
                                    //if (viewModel.PokemonsFave.contains(pokemon))
                                    //  viewModel.PokemonsFave.remove(pokemon)
                                    //else
                                    //  pokemon?.let { viewModel.PokemonsFave.add(it) }

                                }
                            }
                        }
                        .requiredSize(36.dp, 36.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Divider(
            color = Color.Black,
            thickness = 1.5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text(text = "", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.weight(4f))
                EvolutionBar(navController)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Divider(
            color = Color.Black,
            thickness = 1.5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            pokemon?.let {
                Text(
                    text = pokemon.pokedexText,
                    fontFamily = Font.rudaFontFamily,

                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart),
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                )
            }
        }
        Divider(
            color = Color.Black,
            thickness = 1.5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        CatchAndGrowthRateBoxes(viewModel = viewModel)
        /*Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.catchrate),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { catchRateTextBox = !catchRateTextBox }

                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .offset(x = 4.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    if (pokemon != null) {
                        Text(
                            text = pokemon.capture_rate.toString(),
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily
                        )
                    }
                }

                if(catchRateTextBox) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(120.dp)
                            .height(80.dp)
                            .offset(x = 20.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.LightGray)
                            .padding(3.dp)
                    ){
                        Text(
                            text = "This is the catch rate of the Pokemon.",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.growthrate),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { growthRateTextBox = !growthRateTextBox }


                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .offset(x = 4.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    if (pokemon != null) {
                        Text(
                            text = pokemon.growth_rate,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily
                        )
                    }
                }
                if(growthRateTextBox) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(120.dp)
                            .height(80.dp)
                            .offset(x = 20.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.LightGray)
                            .padding(3.dp)
                    ){
                        Text(
                            text = "This is the growth rate of the Pokemon.",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily
                        )
                    }
                }
            }*/
        Divider(
            color = Color.Black,
            thickness = 1.5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        // abilities
        /*Row {
                Text(
                    text = "Abilities",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Font.rudaFontFamily
                )
            }

             */
        //if (isAbilityVisible && pokemon != null){
        Column {
            Row {
                Text(
                    text = "Abilities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Font.rudaFontFamily,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand abilities",
                    modifier = Modifier
                        .size(46.dp)
                        .padding(10.dp)
                        .clickable {
                            isAbilityVisible = !isAbilityVisible
                        }
                        .rotate(if (isAbilityVisible) 180f else 0f)
                )
            }

            if (isAbilityVisible && pokemon != null) {
                //Column {

            pokemon.abilities.forEach { ability ->
                    val isDescriptionVisible = descriptionVisibilityMap[ability] ?: false

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            //text = pokemon.abilities.toString(),
                            text = ability,
                            modifier = Modifier.weight(1f),
                            fontFamily = Font.rudaFontFamily
                        )
                        IconButton(onClick = {
                            descriptionVisibilityMap[ability] = !isDescriptionVisible
                        }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info"
                            )
                        }
                    }

                    if (isDescriptionVisible) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(1.dp, Color.Gray)
                        ) {
                            Text(
                                "Description for $ability",
                                modifier = Modifier.padding(8.dp),
                                fontFamily = Font.rudaFontFamily
                            )
                        }
                    }
                }
            } else if (isAbilityVisible) {
                Text("No abilities found or Pokemon data is not loaded.")
            }
        }
    }
}







@Composable
    fun GenderIcon(
        imageResId: Int,
        selectedGender: Gender,
        onGenderSelected: (Gender) -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable { onGenderSelected(selectedGender) }
                .padding(2.dp)
                .border(
                    width = 2.dp,
                    color = if (selectedGender != Gender.NONE) Color.White else Color.Transparent,
                    //shape = CircleShape
                )
                .padding(2.dp),
        ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            //.padding(3.dp)
            //.size(36.dp)
            //.clickable { onGenderSelected(selectedGender) }
            //.border(
            //  width = 2.dp,
            //color = if (selectedGender != Gender.NONE) Color.White else Color.Transparent,),
            contentScale = ContentScale.Fit
            )
        }
    }
     //           .padding(vertical = 4.dp))
      //  }
   // }

    @Composable
    fun GenderDisplay(genderRate: GenderRate) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when(genderRate.gender) {
                Gender.MALE,Gender.FEMALE,Gender.MIXED -> {
                    if (genderRate.maleRatio > 0) {
                        GenderIcon(
                            imageResId = R.drawable.male,
                            ratio = genderRate.maleRatio,
                            color = Color(0xFF51BAEE)
                        )
                    }
                    if (genderRate.femaleRatio > 0.0) {
                        Spacer(modifier = Modifier.width(4.dp))
                        GenderIcon(
                            imageResId = R.drawable.female,
                            ratio = genderRate.femaleRatio,
                            color = Color(0xFFFF007F)
                        )
                    }
                }
                Gender.NONE -> {
                    GenderIcon(imageResId = R.drawable.male, ratio = 0.0, color = Color(0xFF51BAEE))
                    Spacer(modifier = Modifier.width(4.dp))
                    GenderIcon(imageResId = R.drawable.female, ratio = 0.0, color = Color(0xFFFF007F))
                }
                else->{
                    Text(text = "UNKNOWN")
                }
            }

        }
    }

   @Composable
   fun GenderIcon(imageResId: Int, ratio: Double, color: Color) {
       Column (horizontalAlignment = Alignment.CenterHorizontally){
           Box(
               contentAlignment = Alignment.Center, modifier = Modifier
                   .border(width = 1.dp, color, shape = RoundedCornerShape(50))
                   .padding(3.dp)
           ) {
               Text(
                   text = "${ratio}%",
                   fontSize = 16.sp,
                   fontWeight = FontWeight.Bold,
                   fontFamily = Font.rudaFontFamily,
                   modifier = Modifier
                       .align(Alignment.TopCenter)
               )
           }
           Spacer(modifier = Modifier.height(4.dp))
           Image(
               painter = painterResource(id = imageResId),
               contentDescription = "Gender icon",
               modifier = Modifier
                   .size(36.dp)
           )
       }

   }




enum class Gender {
    MALE, FEMALE, MIXED, NONE, UNKNOWN// None because some rare exist. Maybe gray should be added.
}

@Composable
@Preview(showBackground = true)
fun PokemonShowcasePreview() {
}

@Composable
fun CatchAndGrowthRateBoxes(viewModel: searchPageViewModel) {
    var catchRateTextBox by remember { mutableStateOf(false) }
    var growthRateTextBox by remember { mutableStateOf(false) }
    val pokemon = viewModel.getPokemon()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        /*Image(
            painter = painterResource(id = R.drawable.catchrate),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clickable { catchRateTextBox = !catchRateTextBox }

      )*/

        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(120.dp)
                .height(40.dp)
                //.offset(x = 4.dp)
                .clip(RoundedCornerShape(25.dp))
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { catchRateTextBox = !catchRateTextBox },
            contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                Text(
                    text = pokemon.capture_rate.toString(),
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontFamily = Font.rudaFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(120.dp)
                .height(40.dp)
                // .offset(x = 4.dp)
                .clip(RoundedCornerShape(25.dp))
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { growthRateTextBox = !growthRateTextBox },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pokemon?.growth_rate.toString(),
                color = Color.Gray,
                fontSize = 14.sp,
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (catchRateTextBox) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray)
                    .padding(3.dp)
            ) {
                Text(
                    text = "This is the catch rate of the Pokemon.",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = Font.rudaFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (growthRateTextBox) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray)
                    .padding(3.dp)
            ) {
                Text(
                    text = "This is the growth rate of the Pokemon.",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = Font.rudaFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


val gradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFD4C21B), Color(0xFF76C5DE)))
@Composable
fun GradientBox() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Box(
            modifier = Modifier
                .height(150.dp)
                .width(350.dp)
                .background(Color.Transparent)
                .border(width = 1.dp, brush = gradient, shape = RoundedCornerShape(35.dp))
        )
    }
}