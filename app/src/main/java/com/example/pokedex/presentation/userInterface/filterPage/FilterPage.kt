package com.example.pokedex.presentation.userInterface.filterPage

//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import com.example.pokedex.Presentation.UserInterface

import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.R
import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.theme.Font

class FilterPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        // Initialize ViewModel
        val viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)
        val resetViewModel = ViewModelProvider(this).get(ResetViewModel::class.java)
        setContent {
            // Initialize NavController for Compose
            val navController = rememberNavController()

            // Now pass the navController, viewModel, and resetViewModel instances to the FilterPageContent
            FilterPageContent(navController, viewModel, resetViewModel)
        }
    }
}
@Composable
fun FilterPageContent(navController: NavHostController, viewModel: FilterViewModel, resetViewModel: ResetViewModel) {
    var isGenerationVisible by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .padding(top = 86.dp)
                .fillMaxSize()
        ) {
            // Sorting Section
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Sorting",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        SortButtons(
                            sharedViewModel = resetViewModel,
                            onLowToHighClick = {
                                resetViewModel.Pokemons.sortBy { it.id }
                            },
                            onHighToLowClick = {
                                resetViewModel.Pokemons.sortByDescending { it.id }
                            }
                        )
                    }
                }
            }

            // Filter Section
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Filters",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.5.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )

                        Text(
                            text = "Type",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Font.rudaFontFamily,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        TypeButton(sharedViewModel = resetViewModel)

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.5.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(10.dp)
                                //.clickable { isGenerationVisible = !isGenerationVisible }
                        ) {
                            Column {
                                    Row {
                                        Text(
                                            text = "Generations",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = Font.rudaFontFamily,
                                            modifier = Modifier
                                                .padding(10.dp)
                                        )
                                        //Spacer(modifier = Modifier.width(8.dp))

                                        //AnimatedVisibility(visible = isGenerationVisible) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Expand Generations",
                                            modifier = Modifier
                                                .size(46.dp)
                                                .padding(10.dp)
                                                .clickable {
                                                    isGenerationVisible = !isGenerationVisible
                                                }
                                                .rotate(if (isGenerationVisible) 180f else 0f)
                                            //modifier = Modifier.rotate(if (isGenerationVisible) 180f else 0f)
                                        )
                                    }
                                //}
                                //}
                                /*AnimatedVisibility(visible = isGenerationVisible) {
                                Column {
                                    Button(
                                        onClick = { isGenerationVisible = !isGenerationVisible },
                                        modifier = Modifier.padding(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF58ABF6)
                                        )

                                    ) {
                                        Text(text = "Generations", fontFamily = Font.rudaFontFamily)
                                    }
                                }
                            }

                             */


                                // Checks clickable Gen button and opens up generation loop buttons and stuff.
                                if (isGenerationVisible) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                1.dp,
                                                Color.LightGray,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .padding(16.dp)
                                    ) {
                                        for (generation in 1..9) {
                                            GenerationButton(
                                                generation = generation,
                                                selectedGeneration = resetViewModel.selectedGeneration.value,
                                                onGenerationSelected = {
                                                    if (resetViewModel.selectedGeneration.value == it) {
                                                        resetViewModel.selectedGeneration.value = -1
                                                    } else {
                                                        resetViewModel.selectedGeneration.value = it
                                                    }
                                                },
                                                isGenerationSelected = resetViewModel.selectedGeneration.value == generation,
                                                isNameInGeneration = isNameInGeneration(
                                                    resetViewModel.selectedName.value,
                                                    generation
                                                )
                                            )

                                            if (resetViewModel.selectedGeneration.value == generation) {
                                                GenerationNameList(
                                                    generation = generation,
                                                    selectedName = resetViewModel.selectedName.value,
                                                    onNameSelected = {
                                                        resetViewModel.selectedName.value = it
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp)
                .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navController.navigate(Route.POKEDEX.path)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "backArrow",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .align(Alignment.CenterEnd)
                        .size(46.dp)
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "Filters and sorting",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Font.rudaFontFamily,
                modifier = Modifier
                    .weight(1f)
                // Space Slot here for headline folks!
            )

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "resetButton",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        resetViewModel.resetFilters()
                    }
            )
        }
    }
}

@Composable
fun SortButtons(
    sharedViewModel: ResetViewModel,
    onLowToHighClick: () -> Unit,
    onHighToLowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            shape = RoundedCornerShape(15.dp),
            onClick = {
                sharedViewModel.selectedSortOption.value = SortOption.LowToHigh
                onLowToHighClick()
            },
            //colors = ButtonDefaults.buttonColors(backgroundColor = if (sharedViewModel.selectedSortOption.value == SortOption.LowToHigh) Color.DarkGray else Color.LightGray),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF58ABF6) // Set to your desired color
            ),
            modifier = Modifier
                .border(
                    width = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = if (sharedViewModel.selectedSortOption.value == SortOption.LowToHigh) Color(0xFF006CB8) else Color.Transparent,
                )
        ) {
            Text(
                text = "Low to High",
                fontFamily = Font.rudaFontFamily
            )
        }
        Spacer(modifier = Modifier.width(50.dp))

        Button(
            shape = RoundedCornerShape(15.dp),
            onClick = {
                sharedViewModel.selectedSortOption.value = SortOption.HighToLow
                onHighToLowClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF58ABF6) // Set to your desired color
            ),
            modifier = Modifier
                .border(
                    width = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = if (sharedViewModel.selectedSortOption.value == SortOption.HighToLow) Color(0xFF006CB8) else Color.Transparent,
                )
        ) {
            Text(
                text = "High to Low",
                fontFamily = Font.rudaFontFamily
            )
        }
    }
}

enum class SortOption {
    LowToHigh,
    HighToLow
}
@Composable
fun TypeButton(sharedViewModel: ResetViewModel) {
    var isMenuVisible by remember { mutableStateOf(true) }
    var selectedTypes by remember { mutableStateOf(emptyList<Int>()) }

    /*Button(
        onClick = { isMenuVisible = true },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Type")
    }

     */

    if (isMenuVisible) {
        val types = listOf(
            R.drawable.bugicon,
            R.drawable.dark,
            R.drawable.dragon,
            R.drawable.electric,
            R.drawable.fairy,
            R.drawable.fighting,
            R.drawable.fire,
            R.drawable.flying,
            R.drawable.ghost,
            R.drawable.grass,
            R.drawable.ground,
            R.drawable.iceicla,
            R.drawable.normal,
            R.drawable.poison,
            R.drawable.psychic,
            R.drawable.rock,
            R.drawable.steel,
            R.drawable.water
        )
        val columnsPerRow = 3
        val groupedTypes = types.chunked(columnsPerRow)

        Column {
            for (columnTypes in groupedTypes) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Add padding to the row itself
                    horizontalArrangement = Arrangement.SpaceBetween // This will add equal spacing between the items
                ) {
                    for (type in columnTypes) {
                        TypeItemButton(type, sharedViewModel.selectedTypes.value) { selected ->
                            sharedViewModel.selectedTypes.value = selected
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TypeItemButton(type: Int, selectedTypes: List<Int>, onTypeSelected: (List<Int>) -> Unit) {
    val isButtonClicked = selectedTypes.contains(type)

    Box(
        modifier = Modifier
            .size(119.dp, 32.dp)
            .border(
                width = 3.dp,
                color = if (isButtonClicked) Color(0xFF006CB8) else Color.LightGray,
                //shape = RoundedCornerShape(5.dp)
                shape = RoundedCornerShape(15.dp)
            )
            //Only 2 can be selected.
            .clickable {
                onTypeSelected(
                    if (isButtonClicked) selectedTypes - type
                    else {
                        if (selectedTypes.size < 2) {
                            selectedTypes + type
                        } else {
                            selectedTypes
                        }
                    }
                )
            }
    ) {
        Image(
            painter = painterResource(id = type),
            contentDescription = type.toString(),
            modifier = Modifier
                .fillMaxSize()
                .padding(3.5.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
        )
    }
}


@Composable
fun GenerationButton(
    generation: Int,
    selectedGeneration: Int,
    onGenerationSelected: (Int) -> Unit,
    isGenerationSelected: Boolean,
    isNameInGeneration: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(0.dp)
    ) {
        Button(
            onClick = { onGenerationSelected(generation) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF58ABF6)
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            //Maybe my memory is like a goldfish, but this memory works!
            //val checkedMark = "\u2713"

            //val buttonText = "Generation $generation" + if (isNameInGeneration) "   \u2713" else ""
            val buttonText = "Generation $generation"
            Text(
                text = buttonText,
                fontFamily = Font.rudaFontFamily
            )
        }
        // Checkmark Icon
        if (isNameInGeneration) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Black,
                modifier = Modifier

                    .border(
                        width = 3.dp,
                        color = Color(0xFF58ABF6),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(
                        3.dp
                    )
            )
        }
    }
}

@Composable
fun GenerationNameList(
    generation: Int,
    selectedName: String?,
    onNameSelected: (String) -> Unit
) {
    val namesForGeneration = when (generation) {
        1 -> listOf("Red", "Green", "Blue", "Yellow")
        2 -> listOf("Gold", "Silver", "Crystal")
        3 -> listOf("Ruby", "Sapphire", "Emerald", "FireRed", "LeafGreen")
        4 -> listOf("Diamond", "Pearl", "Platinum", "HeartGold", "SoulSilver")
        5 -> listOf("Black", "White", "Black 2", "White 2")
        6 -> listOf("X", "Y", "Omega Ruby", "Alpha Sapphire")
        7 -> listOf("Sun", "Moon", "Ultra Sun", "Ultra Moon")
        8 -> listOf("Sword", "Shield", "Brilliant Diamond", "Shining Pearl", "Legends: Arceus")
        9 -> listOf("Scarlet", "Violet")
        else -> emptyList()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        namesForGeneration.forEach { name ->
            val isSelected = name == selectedName
            val textModifier = Modifier
                .clickable {
                    onNameSelected(name)
                }
                //.padding(4.dp)
                .border(
                    width = 3.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = (if (isSelected) Color(0xFF58ABF6) else Color.Transparent)
                )
                .padding(8.dp)
            Text(
                text = name,
                modifier = textModifier,
                fontSize = 16.sp,
                fontFamily = Font.rudaFontFamily,
                color = if (isSelected) Color.Black else Color.Black
            )
        }
    }
}


//Laver et memory state, der checker for gens og dens indhold af navn til at rykke checkmarket hen til den generation. RememberState, but fancy.
fun isNameInGeneration(name: String?, generation: Int): Boolean {
    return when (generation) {
        1 -> listOf("Red", "Green", "Blue", "Yellow").contains(name)
        2 -> listOf("Gold", "Silver", "Crystal").contains(name)
        3 -> listOf("Ruby", "Sapphire", "Emerald", "FireRed", "LeafGreen").contains(name)
        4 -> listOf("Diamond", "Pearl", "Platinum", "HeartGold", "SoulSilver").contains(name)
        5 -> listOf("Black", "White", "Black 2", "White 2").contains(name)
        6 -> listOf("X", "Y", "Omega Ruby", "Alpha Sapphire").contains(name)
        7 -> listOf("Sun", "Moon", "Ultra Sun", "Ultra Moon").contains(name)
        8 -> listOf(
            "Sword",
            "Shield",
            "Brilliant Diamond",
            "Shining Pearl",
            "Legends: Arceus"
        ).contains(name)

        9 -> listOf("Scarlet", "Violet").contains(name)
        else -> false
    }
}
