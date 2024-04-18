package com.example.mynotes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mynotes.ui.MainMenu

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainMenu()
        }
        Log.i("MainActivity", "onStart()")
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume()")
    }
}