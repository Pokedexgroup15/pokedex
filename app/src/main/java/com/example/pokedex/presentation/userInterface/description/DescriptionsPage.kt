package com.example.pokedex



import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.data.GenderRate
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.userInterface.HomePage.EvolutionBar
import com.example.pokedex.presentation.userInterface.HomePage.getTypeIconwithID
import com.example.pokedex.presentation.searchPageViewModel
import com.example.pokedex.presentation.theme.Font
import com.example.pokedex.presentation.userInterface.HomePage.VersionBar
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun ShowcasePage(navController: NavHostController, viewModel: searchPageViewModel) {
    val context = LocalContext.current
    val pokemon = viewModel.getPokemon()
    val maleColor = Color(49, 59, 169)
    val femaleColor = Color(143, 68, 124)
    val mixedColor = Color(0xFFF5F5DC)
    val genderlessColor = Color.LightGray
    var catchRateTextBox by remember { mutableStateOf(false) }
    var growthRateTextBox by remember { mutableStateOf(false) }
    var Favorized by remember { mutableStateOf(viewModel.PokemonsFave.value.contains(pokemon)) }
    var isAbilityVisible by remember { mutableStateOf(false) }
    val descriptionVisibilityMap = remember { mutableStateMapOf<String, Boolean>() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.navigate(Route.POKEDEX.path) {
                    popUpTo(navController.graph.findStartDestination().id) {

                        //saveState = true //This line makes all pokemon scroll state remembered.
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

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

                    if (pokemon != null && pokemon.type2 != "null") {
                        Image(
                            painter = painterResource(id = getTypeIconwithID(pokemon.type2)),
                            contentDescription = "type2",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

        }

        //TopBar Workaround, without functionality breaks.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            item {
                Divider(
                    color = Color.Black,
                    thickness = 1.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(vertical = 4.dp)
                )
                val backgroundColor = when (pokemon?.genderRate?.gender) {
                    Gender.MIXED -> mixedColor
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
                                .padding(10.dp)
                                .height(350.dp),
                            contentScale = ContentScale.Crop,

                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomStart)
                    ) {

                        Spacer(modifier = Modifier.width(16.dp))

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
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
                            tint = if (Favorized) Color.Red else Color.Black,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    Favorized = !Favorized

                                    pokemon?.let {
                                        if (Favorized) {
                                            viewModel.toggleFavourite(pokemon, Favorized)
                                            viewModel.PokemonsFave.value.remove(it)


                                        } else {
                                            viewModel.toggleFavourite(pokemon, Favorized)
                                            viewModel.PokemonsFave.value.remove(it)


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
                if (pokemon != null) {
                    EvolutionBar(navController,pokemon,viewModel)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text(text = "", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.weight(4f))
                VersionBar(navController, viewModel)
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
                HeadlineAndInfoBox()
                CatchAndGrowthRateBoxes(viewModel = viewModel)

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
                            val abilityDescription = PokemonObject.abilMap[ability] ?: "No description available"

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
                                        .border(1.dp, Color.LightGray)
                                ) {
                                    Text(
                                        text = abilityDescription,
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

                Divider(
                    color = Color.Black,
                    thickness = 1.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
                FormUI(viewModel = viewModel)
                Divider(
                    color = Color.Black,
                    thickness = 1.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
//This is a spiderchart hackworkaround, because Canvas can't directly take align inputs.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.Center)
                )
                {
                    Text(
                        text = "Base Stats",
                        fontFamily = Font.rudaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)

                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        if (pokemon != null) {
                            SpiderChart(

                                stats = mapOf(
                                    "HP" to pokemon.hp,
                                    "Attack" to pokemon.attack,
                                    "Defense" to pokemon.defense,
                                    "Special Attack" to pokemon.special_attack,
                                    "Special Defense" to pokemon.special_defense,
                                    "Speed" to pokemon.speed

                                ),
                                modifier = Modifier.padding(72.dp),
                            )
                        }
                    }
                }

                val total = (pokemon?.hp ?: 0) +
                        (pokemon?.attack ?: 0) +
                        (pokemon?.defense ?: 0) +
                        (pokemon?.special_attack ?: 0) +
                        (pokemon?.special_defense ?: 0) +
                        (pokemon?.speed ?: 0)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Total : " + total,
                        fontFamily = Font.rudaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier

                    )
                }
            }
        }
    }
}
@Composable
fun FormUI(viewModel: searchPageViewModel) {
    val pokemon = viewModel.getPokemon()

    Text(
        text = "Form",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Font.rudaFontFamily,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "SuperSejform Placeholder text", // Placeholder text, erstat med pokemon.formdescription
                fontSize = 18.sp,
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.Normal
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
        AsyncImage(
            model = pokemon?.pictureURL,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun SpiderChart(stats: Map<String, Int>, modifier: Modifier = Modifier, size: Dp = 255.dp) {
    val maxValue = 255
    val numberOfRings = 5
    val ringSpacing = 20.dp

    Box(
        modifier = modifier
            .size(size)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //Had to use toPx() because dp won't work on canvas with height and width. so it's pixel based.
            val centerX = size.toPx() / 2f
            val centerY = size.toPx() / 2f
            val maxRadius = (size.toPx() / 3).coerceAtMost(size.toPx() / 3)
            val ringRadius = maxRadius / numberOfRings
            // Draw 5 ring within the canvas, based on pixel.
            //If more time,try to go away from toPx()
            for (i in 1..numberOfRings) {
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.3f),
                    radius = i * ringSpacing.toPx() + ringRadius,
                    center = Offset(centerX, centerY),
                    style = Stroke(2f)
                )
            }

            val path = Path()
            // Draws stats and 6 titles
            stats.entries.forEachIndexed { index, pokeStatEntry ->
                val pokeStatValue = pokeStatEntry.value.coerceIn(0, maxValue)
                val angle = 2 * PI * index / stats.size.toFloat()
                val x = centerX + maxRadius * pokeStatValue / maxValue * cos(angle)
                val y = centerY + maxRadius * pokeStatValue / maxValue * sin(angle)

                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(x.toFloat(), y.toFloat()),
                    strokeWidth = 2f
                )
                // calc pos of the text of "overtitle" such as special defense fx.
                val titleDistance = maxRadius + 60f
                val titleXpos = centerX + titleDistance * cos(angle)
                val titleYpos = centerY + titleDistance * sin(angle)
                // workaround hack, not the best, but works with key collection.
                //ChatGPT helped created the below code, to paint onto the canvas with text drawing.
                //ChatGPT was used since canvas was unknown territory.
                drawContext.canvas.nativeCanvas.drawText(
                    pokeStatEntry.key,
                    titleXpos.toFloat(),
                    titleYpos.toFloat(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )

                val statValueX = centerX + titleDistance * cos(angle)
                val statValueY = centerY + titleDistance * sin(angle) + 60f
                drawContext.canvas.nativeCanvas.drawText(
                    pokeStatValue.toString(),
                    statValueX.toFloat(),
                    statValueY.toFloat(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 16.sp.toPx()
                        isFakeBoldText
                        textAlign = Paint.Align.CENTER
                    }
                )

                if (index == 0) {
                    path.moveTo(x.toFloat(), y.toFloat())
                } else {
                    path.lineTo(x.toFloat(), y.toFloat())
                }
            }
            //ChatGPT generated code help stopped here.
            // important line, fragile.
            path.close()

            drawPath(
                path = path,
                color = Color(0xFF006CB8),
                style = Fill
            )
            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(2f)
            )
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
                GenderIcon(
                    imageResId = R.drawable.male,
                    ratio = 0.0,
                    color = Color(0xFF51BAEE))
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
                    .padding(4.dp)
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
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                text = "Catch Rate: ${pokemon?.capture_rate ?: "N/A"}",
                color = Color.Gray,
                fontSize = 15.sp,
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                text = "Growth Rate: ${pokemon?.growth_rate ?: "N/A"}",
                color = Color.Gray,
                fontSize = 15.sp,
                fontFamily = Font.rudaFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )
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

@Composable
fun HeadlineAndInfoBox() {
    var infoBox by remember { mutableStateOf(false) }
    Box {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Catch and Growth Rates",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -4.dp)
                    .clickable { infoBox = !infoBox }
            )
        }
    }


    if (infoBox) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(130.dp)
                    .width(350.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Catch rate on the left, Growth rate on the right. " +
                            "The catch rate shows the difficulity of catching the Pokemon." +
                            "The growth rate means the rate of which the Pokemon gains XP and levels up. ",
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}