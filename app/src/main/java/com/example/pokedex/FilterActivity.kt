package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val actionBar = supportActionBar
        actionBar!!.title = "Filter Activity"
        actionBar.setDisplayHomeAsUpEnabled(true)
        //val i = intent
        //val msg = i.getStringExtra("type")
        //Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    fun backClick(view: View) {
        this.finish()

    }
}