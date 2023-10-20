package com.example.pokedex

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.filterButton)
        button.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,FilterActivity::class.java)
        }



        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("GUYS")
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
        modifier = modifier
    )
}
@Composable
fun DemoScreen() {
    var isOn by remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxSize(), Alignment.TopCenter) {
        //Box(modifier = Modifier.size(40.dp), Alignment.TopCenter) {
        SearchButton(isOn) {
            isOn = !isOn
        }
    }
        Box(Modifier.fillMaxSize(), Alignment.TopEnd) {
        FilterButton(isOn) {
            isOn = !isOn
        }
    }
}
@Composable
fun SearchButton(isOn: Boolean, onClick: () -> Unit) {
    val context = LocalContext.current
    //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
    val intent = Intent(context, FilterActivity::class.java)


    Button(onClick = onClick) {

        if (isOn) {
            startActivity(context,intent,null)
            //Text(text = "Off")
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
            Text("On")
        }
    }
}
