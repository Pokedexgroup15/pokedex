package com.example.pokedex.presentation.userInterface.HomePage
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.pokedex.R
import com.example.pokedex.presentation.theme.Font


@Composable
fun pokemonPage(navController: NavHostController){
    //var showEvolutions by remember { mutableStateOf(true) }

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
            EvolutionBar(navController)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun EvolutionBar(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 10.dp)
    ) {
        Text(
            text = "Evolutions",
            fontSize = 20.sp,
            fontFamily = Font.rudaFontFamily,
            modifier = Modifier
        )
    }
Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        EvolutionCircleImage(navController)
        EvolutionArrowImage()
        EvolutionCircleImage(navController)
        EvolutionArrowImage()
        EvolutionCircleImage(navController)
        /*repeat(3){
            EvolutionCircleImage()
            }*/
    }
}


@Composable
fun EvolutionCircleImage(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .clip(CircleShape)
            .clickable() {
                navController.navigate("DescriptionsPage")
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
        EvolutionsPicture()
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
fun EvolutionsPicture(){
    Image(
        painter = painterResource(id = R.drawable.raichu),
        modifier = Modifier
            .size(65.dp)
            .offset(y = -10.dp)
            .offset(x = 3.dp),
        contentDescription = null
    )
}

@Composable
fun VersionBar(navController: NavHostController) {
    var isVersionVisible by remember { mutableStateOf(false) }
    //val descriptionVisibilityMap = remember { mutableStateMapOf<String, Boolean>() }
    Column {
        Row {
            Text(
                text = "Versions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Font.rudaFontFamily,
                modifier = Modifier
                    .padding(10.dp)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand versions",
                modifier = Modifier
                    .size(46.dp)
                    .padding(10.dp)
                    .clickable {
                        isVersionVisible = !isVersionVisible
                    }
                    .rotate(if (isVersionVisible) 180f else 0f)
            )
        }

        if (isVersionVisible) {
            //Column {
            /*
            pokemon.versions.forEach { version ->
                val isDescriptionVisible = versionPicVisibilityMap[version] ?: false

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        //text = pokemon.abilities.toString(),
                        text = version,
                        modifier = Modifier.weight(1f),
                        fontFamily = Font.rudaFontFamily
                    )
                    IconButton(onClick = {
                        versionPicVisibilityMap[version] = !isVersionVisible
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
                            "Description for $version",
                            modifier = Modifier.padding(8.dp),
                            fontFamily = Font.rudaFontFamily
                        )
                    }
                }
            }
        } else if (isVersionVisible) {
            Text("No abilities found or Pokemon data is not loaded.")
        }
    }
    }

 */
        }
    }
}