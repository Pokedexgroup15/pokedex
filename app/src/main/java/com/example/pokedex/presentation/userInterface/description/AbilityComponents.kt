package com.example.pokedex.presentation.userInterface.description

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun AbilityRow(abilityName: String, onInfoClicked: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = abilityName,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onInfoClicked) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        }
    }
}

@Composable
fun AbilityDescriptionBox(description: String, isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            Text(text = description)
        }
    }
}

@Composable
fun AbilityListWithDescription(abilities: List<String>) {
    val abilityDescriptionVisibility = remember { mutableStateMapOf<String, Boolean>() }

    Column {
        abilities.forEach { ability ->
            AbilityRow(abilityName = ability, onInfoClicked = {
                // Toggle visibility of the description box
                abilityDescriptionVisibility[ability] = !(abilityDescriptionVisibility[ability] ?: false)
            })

            // Mock description for demonstration. Replace with actual description from your API
            val description = "Description for $ability"
            AbilityDescriptionBox(description, abilityDescriptionVisibility[ability] ?: false)
        }
    }
}

@Composable
fun AbilityItem(ability: String, descriptionVisibilityMap: MutableMap<String, Boolean>) {
    var isDescriptionVisible by remember { mutableStateOf(descriptionVisibilityMap[ability] ?: false) }

    Row {
        Text(text = ability)
        IconButton(onClick = {
            isDescriptionVisible = !isDescriptionVisible
            descriptionVisibilityMap[ability] = isDescriptionVisible
        }) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        }
    }

    if (isDescriptionVisible) {
        Text("Description for $ability")
        // Add description logic here
    }
}
