package com.example.pokedex
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.example.pokedex.R.layout.activity_search_bar
import com.example.pokedex.ui.theme.PokedexTheme

open class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // val Nav = findViewById<Button>(R.id.button);
        //Nav.setOnClickListener(View.OnClickListener {

        //val intent = Intent(this.SearchBarActivity::class.java)
        //  startActivity(intent)
        //})
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Pokemon List")
                    DemoScreen()
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        //alignment = Alignment.BottomCenter,
        modifier = modifier
    )
}
@Composable
fun DemoScreen() {
    var isOn by remember {
        mutableStateOf(false)
    }
    var filterisOn by remember {
        mutableStateOf(false)
    }
    Box(Modifier.size(64.dp), Alignment.TopEnd) {
        //Box(modifier = Modifier.size(40.dp), Alignment.TopCenter) {
        SearchButton(isOn) {
            isOn = !isOn
        }
    }
    Box(Modifier.size(128.dp), Alignment.TopCenter) {
        FilterButton(filterisOn) {
            filterisOn = !filterisOn
        }
    }
}
@Composable
fun SearchButton(isOn: Boolean, onClick: () -> Unit) {

    Button(onClick = onClick) {
        if (isOn) {
            Text(text = "Off")
        } else {
            Text("🔍")
        }
    }
}
@Composable
fun FilterButton(isOn: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick) {
        if (isOn) {
            Text(text = "Off")
        } else {
            Text("❤️")
        }
    }
}