package com.example.pokedex



import android.content.Intent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.domain.Pokemon
import com.example.pokedex.presentation.userInterface.HomePage.EvolutionBar
import com.example.pokedex.presentation.userInterface.HomePage.getTypeIconwithID
import com.example.pokedex.presentation.searchPageViewModel
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.sin



@Composable
    fun ShowcasePage(navController: NavHostController,viewModel: searchPageViewModel) {
        val context = LocalContext.current
        var selectedGender by remember { mutableStateOf(Gender.NONE) }
        val pokemon = viewModel.getPokemon()
        val maleColor = Color(49,59,169)
        val femaleColor = Color(143,68,124)
        var catchRateTextBox by remember { mutableStateOf(false) }
        var growthRateTextBox by remember { mutableStateOf(false) }
        var Favorized by remember { mutableStateOf(viewModel.PokemonsFave.value.contains(pokemon)) }
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
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                //Spacer(modifier = Modifier.width(14.dp))
                //Texten skal retrieve en string fra PokeAPI'en.

                viewModel.getPokemon()?.let {
                    Text(
                        text = it.name,
                        fontSize = 30.sp,
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        when (selectedGender) {
                            Gender.MALE -> maleColor
                            Gender.FEMALE -> femaleColor
                            else -> Color.Transparent // Or grey depends on logic.
                        }
                    )
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
                horizontalArrangement = Arrangement.Start
            ) {
                GenderIcon(
                    imageResId = R.drawable.male,
                    selectedGender = Gender.MALE,
                    onGenderSelected = { selectedGender = it }
                )

                GenderIcon(
                    imageResId = R.drawable.female,
                    selectedGender = Gender.FEMALE,
                    onGenderSelected = { selectedGender = it }
                )
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
//Indsæt det gamle pis udcursed.
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
                    .align(alignment = CenterHorizontally)
            )
            {
                Text(
                    text = "Base Stats",
                    fontFamily = com.example.pokedex.presentation.theme.Font.rudaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)

                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    SpiderChart(

                        stats = mapOf(
                            "HP" to 140f,
                            "Attack" to 60f,
                            "Defense" to 70f,
                            "Special Attack" to 45f,
                            "Special Defense" to 45f,
                            "Speed" to 20f

                        ),
                        modifier = Modifier.padding(72.dp),
                    )
                }
            }
        }
    }
@Composable
fun FormUI(viewModel: searchPageViewModel) {
    val pokemon = viewModel.getPokemon()

    Text(
        text = "Form",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
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
                //Hvis behov for testing så brug dette.
                //.border(1.dp, Color.Black)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
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


enum class Gender {
    MALE, FEMALE, NONE // None because some rare exist. Maybe gray should be added.
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
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun SpiderChart(stats: Map<String, Float>, modifier: Modifier = Modifier, size: Dp = 255.dp) {
    val maxValue = 255f
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
            //Had to use toPx() because dp won't work on canvas with height and width.
            val centerX = size.toPx() / 2f
            val centerY = size.toPx() / 2f
            val maxRadius = (size.toPx() / 3).coerceAtMost(size.toPx() / 3)
            val ringRadius = maxRadius / numberOfRings
            // Draws 5 rings with size max 100
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
                val pokeStatValue = pokeStatEntry.value.coerceIn(0f, maxValue)
                val angle = 2 * PI * index / stats.size.toFloat()
                val x = centerX + maxRadius * pokeStatValue / maxValue * cos(angle)
                val y = centerY + maxRadius * pokeStatValue / maxValue * sin(angle)

                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(x.toFloat(), y.toFloat()),
                    strokeWidth = 2f
                )
                // calc pos based on center y and x
                val titleDistance = maxRadius + 40f
                val titleX = centerX + titleDistance * cos(angle)
                val titleY = centerY + titleDistance * sin(angle)
                // workaround hack, not the best, but works with key collection.
                drawContext.canvas.nativeCanvas.drawText(
                    pokeStatEntry.key,
                    titleX.toFloat(),
                    titleY.toFloat(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )


                val statValueX = centerX + titleDistance * cos(angle)
                val statValueY = centerY + titleDistance * sin(angle) + 20f
                drawContext.canvas.nativeCanvas.drawText(
                    pokeStatValue.toString(),
                    statValueX.toFloat(),
                    statValueY.toFloat(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )

                if (index == 0) {
                    path.moveTo(x.toFloat(), y.toFloat())
                } else {
                    path.lineTo(x.toFloat(), y.toFloat())
                }
            }
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

