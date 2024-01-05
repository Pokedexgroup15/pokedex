package com.example.pokedex.presentation.userInterface.filterPage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.example.pokedex.Presentation.UserInterface
import com.example.pokedex.presentation.userInterface.filterPage.ResetViewModel
import com.example.pokedex.R

import com.example.pokedex.presentation.navigation.Route
import com.example.pokedex.presentation.userInterface.filterPage.FilterViewModel

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
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterPageContent(navController: NavHostController, viewModel: FilterViewModel, resetViewModel: ResetViewModel) {
    var isGenerationVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(86.dp),
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
                    modifier = Modifier
                        .weight(1f)
                //Space Slot here for headline folks!
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
        //Here begins filter- beneath topbar and sort.
        item {
            Box(
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Filters",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    TypeButton(sharedViewModel = resetViewModel)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Generation Button
                    Button(
                        onClick = { isGenerationVisible = !isGenerationVisible },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Generations")
                    }
                }
            }
        }
        //Checks clickable Gen button and upens up generation loop buttons and stuff.
        if (isGenerationVisible) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
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
                            isNameInGeneration = isNameInGeneration(resetViewModel.selectedName.value, generation)
                        )

                        if (resetViewModel.selectedGeneration.value == generation) {
                            GenerationNameList(
                                generation = generation,
                                selectedName = resetViewModel.selectedName.value,
                                onNameSelected = { resetViewModel.selectedName.value = it }
                            )
                        }
                    }
                }
            }
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            shape = RectangleShape,
            onClick = {
                sharedViewModel.selectedSortOption.value = SortOption.LowToHigh
                onLowToHighClick()
            },
            modifier = Modifier
                .border(
                    width = 4.dp,
                    color = if (sharedViewModel.selectedSortOption.value == SortOption.LowToHigh) Color.DarkGray else Color.Transparent,
                )
        ) {
            Text(text = "Low to High")
        }

        Button(
            shape = RectangleShape,
            onClick = {
                sharedViewModel.selectedSortOption.value = SortOption.HighToLow
                onHighToLowClick()
            },
            modifier = Modifier
                .border(
                    width = 4.dp,
                    color = if (sharedViewModel.selectedSortOption.value == SortOption.HighToLow) Color.DarkGray else Color.Transparent,
                )
        ) {
            Text(text = "High to Low")
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

    Button(
        onClick = { isMenuVisible = true },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Type")
    }

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
                Row {
                    for (type in columnTypes) {
                        TypeItemButton(type, sharedViewModel.selectedTypes.value) { selected ->
                            sharedViewModel.selectedTypes.value = selected
                        }
                        Spacer(modifier = Modifier.width(10.dp))
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
            .size(140.dp, 31.dp)
            .border(
                width = 2.dp,
                color = if (isButtonClicked) Color.Green else Color.Gray,
                shape = RectangleShape
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
    Button(
        onClick = { onGenerationSelected(generation) },
        modifier = Modifier.padding(16.dp)
    ) {
        //Maybe my memory is like a goldfish, but this memory works!
        val buttonText = "Generation $generation" + if (isNameInGeneration) " ✅" else ""
        Text(text = buttonText)
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
                .padding(4.dp)
                .background(if (isSelected) Color.Red else Color.Transparent)
            Text(
                text = name,
                modifier = textModifier,
                fontSize = 16.sp,
                color = if (isSelected) Color.White else Color.Black
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
