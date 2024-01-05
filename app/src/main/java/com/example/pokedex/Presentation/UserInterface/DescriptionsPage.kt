package com.example.pokedex



import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.Presentation.UserInterface.EvolutionBar
import com.example.pokedex.viweModel.searchPageViewModel

    @Composable
    fun ShowcasePage(navHostController: NavHostController,viewModel: searchPageViewModel) {
        val context = LocalContext.current
        val pokemon = viewModel.getPokemon()
        val maleColor = Color(49,59,169)
        val femaleColor = Color(143,68,124)
        val mixedColor= Color(0xFFF5F5DC)
        val genderlessColor = Color.LightGray
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
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(14.dp))
                //Texten skal retrieve en string fra PokeAPI'en.

                pokemon?.let {
                    Text(
                        text = it.name,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
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
            val backgroundColor=when (pokemon?.gender){
               Gender.MALE ->maleColor
                Gender.MIXED -> mixedColor
               Gender.FEMALE-> femaleColor
               Gender.NONE-> genderlessColor
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
                horizontalArrangement = Arrangement.Start
            ) {
                when(pokemon?.gender){
                    Gender.MIXED -> {
                        GenderIcon(imageResId = R.drawable.male)
                        GenderIcon(imageResId = R.drawable.female)
                        Text(
                            text = "50% 50%",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Gender.MALE -> {
                        GenderIcon(imageResId = R.drawable.male)
                    }
                    Gender.FEMALE -> {
                        GenderIcon(imageResId = R.drawable.female)
                    }
                    Gender.NONE -> {
                        Text(text = "GENDERLESS",
                            modifier= Modifier.align(Alignment.CenterVertically),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                    }
                    else -> null
                }
               // GenderIcon(
                   // imageResId = R.drawable.male,
                  //  selectedGender = Gender.MALE,
                  //  onGenderSelected = { selectedGender = it }
               // )

               // GenderIcon(
                  //  imageResId = R.drawable.female,
                  //selectedGender = Gender.FEMALE,
                   // onGenderSelected = { selectedGender = it }
               // )
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pokeball_bw),
                        contentDescription = "Favorite option",
                        tint = if (viewModel.PokemonsFave.contains(pokemon)) Color.Red else Color.Black,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                if (viewModel.PokemonsFave.contains(pokemon))
                                    viewModel.PokemonsFave.remove(pokemon)
                                else
                                    pokemon?.let { viewModel.PokemonsFave.add(it) }
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
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ){
                    Text(text = "", modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.weight(4f))
                    EvolutionBar()
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
                    .padding(16.dp)
                    .height(220.dp)
                    .background(Color.LightGray)
                    .clip(CircleShape)
            ) {
                if (pokemon != null) {
                    Text(
                        text = pokemon.pokedexText[0],
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterStart),
                        color = Color.White
                    )
                }
            }
        }
    }
   @Composable
   fun GenderIcon(imageResId: Int) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Gender icon",
            modifier = Modifier
                .size(36.dp)
        )
    }




enum class Gender {
    MALE, FEMALE, MIXED, NONE, UNKNOWN// None because some rare exist. Maybe gray should be added.
}

@Composable
@Preview(showBackground = true)
fun PokemonShowcasePreview() {
}